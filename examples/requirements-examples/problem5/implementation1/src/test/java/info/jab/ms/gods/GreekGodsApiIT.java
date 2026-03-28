package info.jab.ms.gods;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import info.jab.ms.gods.service.GreekGodsSyncService;
import info.jab.ms.gods.testsupport.AbstractGreekGodsPostgresIntegrationTest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("US-001 Greek Gods API (integration)")
class GreekGodsApiIT {

	private static final Set<String> CANONICAL_20 = Set.of(
			"Zeus",
			"Hera",
			"Poseidon",
			"Demeter",
			"Ares",
			"Athena",
			"Apollo",
			"Artemis",
			"Hephaestus",
			"Aphrodite",
			"Hermes",
			"Dionysus",
			"Hades",
			"Hypnos",
			"Nike",
			"Janus",
			"Nemesis",
			"Iris",
			"Hecate",
			"Tyche");

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Nested
	@DisplayName("PostgreSQL-backed API")
	class PostgresBackedTests extends AbstractGreekGodsPostgresIntegrationTest {

		@Test
		@Tag("smoke")
		@Tag("happy-path")
		@Sql(scripts = "/test-data/greek-gods-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		void smokeAndHappyPath_returnsTwentyNamesOrderedWithCanonicalSet() {
			List<String> body = client
					.get()
					.uri("/api/v1/gods/greek")
					.retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {});

			assertThat(body).hasSize(20);
			assertThat(new HashSet<>(body)).isEqualTo(CANONICAL_20);
			assertThat(body).isSortedAccordingTo(String::compareTo);
		}

		@Test
		@Tag("error-handling")
		@Sql(scripts = "/test-data/clear-greek-gods.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		@Sql(scripts = "/test-data/greek-gods-seed.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
		void emptyDatabase_returnsEmptyJsonArray() {
			List<String> body = client
					.get()
					.uri("/api/v1/gods/greek")
					.retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {});

			assertThat(body).isEmpty();
		}

		@Test
		@Tag("performance")
		@Sql(scripts = "/test-data/greek-gods-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		void singleGet_completesWithinOneSecond() {
			Instant start = Instant.now();
			client.get().uri("/api/v1/gods/greek").retrieve().toBodilessEntity();
			Duration elapsed = Duration.between(start, Instant.now());
			assertThat(elapsed).isLessThan(Duration.ofSeconds(1));
		}

		@Test
		@Tag("performance")
		@Tag("load-testing")
		@Sql(scripts = "/test-data/greek-gods-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		void hundredConcurrentGets_completeWithinTwoSecondsWallClock() throws Exception {
			ExecutorService pool = Executors.newFixedThreadPool(100);
			try {
				CountDownLatch latch = new CountDownLatch(100);
				Instant start = Instant.now();
				for (int i = 0; i < 100; i++) {
					pool.submit(() -> {
						try {
							List<String> body = client
									.get()
									.uri("/api/v1/gods/greek")
									.retrieve()
									.body(new ParameterizedTypeReference<List<String>>() {});
							assertThat(body).hasSize(20);
							assertThat(new HashSet<>(body)).isEqualTo(CANONICAL_20);
						}
						finally {
							latch.countDown();
						}
					});
				}
				assertThat(latch.await(2, TimeUnit.MINUTES)).isTrue();
				Duration wall = Duration.between(start, Instant.now());
				assertThat(wall).isLessThanOrEqualTo(Duration.ofSeconds(2));
			}
			finally {
				pool.shutdown();
				assertThat(pool.awaitTermination(30, TimeUnit.SECONDS)).isTrue();
			}
		}

		@Test
		@Tag("api-specification")
		@Sql(scripts = "/test-data/greek-gods-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		void get_returnsJsonArrayOfStrings() throws Exception {
			var entity = client.get().uri("/api/v1/gods/greek").retrieve().toEntity(String.class);
			assertThat(entity.getStatusCode().value()).isEqualTo(200);
			assertThat(entity.getHeaders().getContentType()).isNotNull();
			assertThat(entity.getHeaders().getContentType().toString()).contains("application/json");
			List<String> parsed = OBJECT_MAPPER.readValue(
					entity.getBody(), OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, String.class));
			assertThat(parsed).hasSize(20);
			assertThat(parsed.get(0)).isInstanceOf(String.class);
		}
	}

	@Nested
	@DisplayName("Database failure handling")
	class WhenDatabaseIsUnreachable extends AbstractGreekGodsPostgresIntegrationTest {

		@Autowired
		private DataSource dataSource;

		@Test
		@Tag("error-handling")
		void get_returnsProblemJsonWithNormativeFields() throws Exception {
			assertThat(dataSource).isInstanceOf(HikariDataSource.class);
			((HikariDataSource) dataSource).close();

			String json = client
					.get()
					.uri("/api/v1/gods/greek")
					.exchange((request, response) -> {
						assertThat(response.getStatusCode().value()).isEqualTo(500);
						String ct = response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
						assertThat(ct).isNotNull();
						assertThat(ct).contains("application/problem+json");
						return new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
					});

			JsonNode node = OBJECT_MAPPER.readTree(json);
			assertThat(node.get("type").asText()).isNotBlank();
			assertThat(node.get("title").asText()).isNotBlank();
			assertThat(node.get("status").asInt()).isEqualTo(500);
		}
	}

	@Nested
	@DisplayName("Upstream sync data quality")
	class DataQualitySyncTests extends AbstractGreekGodsPostgresIntegrationTest {

		private static final WireMockServer WIRE_MOCK =
				new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());

		static {
			WIRE_MOCK.start();
		}

		@AfterAll
		static void stopWireMock() {
			WIRE_MOCK.stop();
		}

		@DynamicPropertySource
		static void registerWireMockBaseUrl(DynamicPropertyRegistry registry) {
			registry.add("greek-gods.sync.base-url", () -> "http://localhost:" + WIRE_MOCK.port());
		}

		@Autowired
		private GreekGodsSyncService syncService;

		@Override
		protected void resetForTest() {
			WIRE_MOCK.resetAll();
		}

		@Test
		@Tag("data-quality")
		@Sql(scripts = "/test-data/clear-greek-gods.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
		void syncFromWireMock_thenGet_reflectsStubbedNames() {
			WIRE_MOCK.stubFor(
					get(urlEqualTo("/greek")).willReturn(aResponse()
							.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
							.withBody("[\"AlphaWireMock\",\"ZetaWireMock\"]")));

			syncService.syncFromUpstream();

			List<String> body = client
					.get()
					.uri("/api/v1/gods/greek")
					.retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {});

			assertThat(body).containsExactly("AlphaWireMock", "ZetaWireMock");
		}
	}
}
