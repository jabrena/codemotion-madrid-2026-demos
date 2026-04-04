package info.jab.ms.gods.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
public class GreekGodsSyncService {

	private static final Logger log = LoggerFactory.getLogger(GreekGodsSyncService.class);

	private static final String UPSERT =
			"""
			INSERT INTO greek_god (name) VALUES (:name)
			ON CONFLICT (name) DO NOTHING
			""";

	private final RestClient greekGodsSyncRestClient;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public GreekGodsSyncService(
			@Qualifier("greekGodsSync") RestClient greekGodsSyncRestClient,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.greekGodsSyncRestClient = greekGodsSyncRestClient;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Transactional
	public void syncFromUpstream() {
		log.debug("Starting Greek gods upstream sync");
		try {
			List<String> names = greekGodsSyncRestClient
					.get()
					.uri("/greek")
					.retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {});
			if (names == null || names.isEmpty()) {
				log.info("Upstream returned no Greek god names; repository unchanged");
				return;
			}
			LinkedHashSet<String> distinctNames = new LinkedHashSet<>();
			for (String raw : names) {
				if (raw == null || raw.isBlank()) {
					continue;
				}
				distinctNames.add(raw.trim());
			}
			if (distinctNames.isEmpty()) {
				log.info("Upstream returned no usable Greek god names after trimming; repository unchanged");
				return;
			}
			SqlParameterSource[] batch = distinctNames.stream()
					.map(name -> new MapSqlParameterSource("name", name))
					.toArray(SqlParameterSource[]::new);
			int[] updates = namedParameterJdbcTemplate.batchUpdate(UPSERT, batch);
			int inserted = Arrays.stream(updates).sum();
			log.info(
					"Greek gods sync finished: upstream count={}, distinct names={}, rows inserted (new)={}",
					names.size(),
					distinctNames.size(),
					inserted);
		}
		catch (Exception ex) {
			log.warn("Greek gods upstream sync failed; will retry on next schedule tick: {}", ex.getMessage());
			log.debug("Greek gods sync failure detail", ex);
		}
	}
}
