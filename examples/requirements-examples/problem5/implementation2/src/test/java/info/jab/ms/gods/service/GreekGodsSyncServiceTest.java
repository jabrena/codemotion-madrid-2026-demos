package info.jab.ms.gods.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import info.jab.ms.gods.repository.GreekGodRepository;
import info.jab.ms.gods.repository.GreekGodsDataAccessException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GreekGodsSyncServiceTest {

    @Mock
    GreekGodsUpstreamClient upstreamClient;

    @Mock
    GreekGodRepository repository;

    @InjectMocks
    GreekGodsSyncService syncService;

    @Test
    void syncFromUpstream_whenUpstreamReturnsNull_doesNotUpsert() {
        when(upstreamClient.getExternalGreekGods()).thenReturn(null);
        syncService.syncFromUpstream();
        verify(repository, never()).upsertNamesBatch(any());
    }

    @Test
    void syncFromUpstream_whenUpstreamReturnsEmpty_doesNotUpsert() {
        when(upstreamClient.getExternalGreekGods()).thenReturn(List.of());
        syncService.syncFromUpstream();
        verify(repository, never()).upsertNamesBatch(any());
    }

    @Test
    void syncFromUpstream_whenAllValuesBlank_doesNotUpsert() {
        when(upstreamClient.getExternalGreekGods()).thenReturn(Arrays.asList(" ", "", null));
        syncService.syncFromUpstream();
        verify(repository, never()).upsertNamesBatch(any());
    }

    @Test
    void syncFromUpstream_whenNamesPresent_callsBatchUpsertWithDistinctTrimmedOrder() {
        when(upstreamClient.getExternalGreekGods()).thenReturn(List.of(" Zeus ", "Zeus", "Hera"));
        when(repository.upsertNamesBatch(any())).thenReturn(2);
        syncService.syncFromUpstream();
        verify(repository).upsertNamesBatch(List.of("Zeus", "Hera"));
    }

    @Test
    void syncFromUpstream_whenUpstreamThrows_doesNotPropagate() {
        when(upstreamClient.getExternalGreekGods()).thenThrow(new RuntimeException("upstream down"));
        assertThatCode(() -> syncService.syncFromUpstream()).doesNotThrowAnyException();
        verify(repository, never()).upsertNamesBatch(any());
    }

    @Test
    void syncFromUpstream_whenRepositoryThrowsDataAccess_doesNotPropagate() {
        when(upstreamClient.getExternalGreekGods()).thenReturn(List.of("Apollo"));
        doThrow(new GreekGodsDataAccessException("db", new RuntimeException()))
                .when(repository)
                .upsertNamesBatch(any());
        assertThatCode(() -> syncService.syncFromUpstream()).doesNotThrowAnyException();
    }
}
