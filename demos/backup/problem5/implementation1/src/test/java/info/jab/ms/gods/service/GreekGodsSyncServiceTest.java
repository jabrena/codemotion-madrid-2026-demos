package info.jab.ms.gods.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import org.mockito.ArgumentMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreekGodsSyncServiceTest {

	@Mock(answer = RETURNS_DEEP_STUBS)
	private RestClient greekGodsSyncRestClient;

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@InjectMocks
	private GreekGodsSyncService syncService;

	@Test
	void syncFromUpstream_insertsNonBlankNames() {
		List<String> upstream = new ArrayList<>(List.of(" Zeus ", "", "  ", "Athena"));
		upstream.add(null);
		when(greekGodsSyncRestClient
						.get()
						.uri(anyString())
						.retrieve()
						.body(any(ParameterizedTypeReference.class)))
				.thenReturn(upstream);
		when(namedParameterJdbcTemplate.batchUpdate(
						anyString(), ArgumentMatchers.<SqlParameterSource[]>any()))
				.thenReturn(new int[] {1, 1});
		Mockito.clearInvocations(namedParameterJdbcTemplate, greekGodsSyncRestClient);

		assertThatCode(() -> syncService.syncFromUpstream()).doesNotThrowAnyException();

		verify(namedParameterJdbcTemplate)
				.batchUpdate(
						anyString(),
						argThat((SqlParameterSource[] sources) -> sources != null && sources.length == 2));
	}

	@Test
	void syncFromUpstream_nullBody_doesNotTouchDatabase() {
		when(greekGodsSyncRestClient
						.get()
						.uri(anyString())
						.retrieve()
						.body(any(ParameterizedTypeReference.class)))
				.thenReturn(null);

		syncService.syncFromUpstream();

		verify(namedParameterJdbcTemplate, never())
				.batchUpdate(anyString(), ArgumentMatchers.<SqlParameterSource[]>any());
	}

	@Test
	void syncFromUpstream_emptyList_doesNotTouchDatabase() {
		when(greekGodsSyncRestClient
						.get()
						.uri(anyString())
						.retrieve()
						.body(any(ParameterizedTypeReference.class)))
				.thenReturn(List.of());

		syncService.syncFromUpstream();

		verify(namedParameterJdbcTemplate, never())
				.batchUpdate(anyString(), ArgumentMatchers.<SqlParameterSource[]>any());
	}

	@Test
	void syncFromUpstream_restClientFailure_doesNotPropagate() {
		when(greekGodsSyncRestClient.get()).thenThrow(new RuntimeException("upstream down"));

		assertThatCode(() -> syncService.syncFromUpstream()).doesNotThrowAnyException();
	}
}
