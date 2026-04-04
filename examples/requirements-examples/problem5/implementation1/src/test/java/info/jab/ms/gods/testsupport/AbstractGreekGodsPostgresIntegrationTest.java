package info.jab.ms.gods.testsupport;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Shared PostgreSQL (Testcontainers), Spring Boot test slice, and HTTP {@link RestClient} for
 * integration tests ({@code *IT}). Subclasses may override {@link #resetForTest()} to clear stubs or
 * other per-test state before the client is rebuilt.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractGreekGodsPostgresIntegrationTest {

	@Container
	protected static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

	@DynamicPropertySource
	static void registerPostgres(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
		registry.add("spring.datasource.username", POSTGRES::getUsername);
		registry.add("spring.datasource.password", POSTGRES::getPassword);
	}

	@LocalServerPort
	protected int port;

	protected RestClient client;

	@BeforeEach
	void prepareEach() {
		resetForTest();
		client = RestClient.builder().baseUrl("http://localhost:" + port).build();
	}

	/**
	 * Override to reset WireMock stubs, clear external state, etc. Runs before {@link #client} is
	 * recreated for each test.
	 */
	protected void resetForTest() {
		// default: no-op
	}
}
