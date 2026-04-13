package info.jab.ms.gods.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GreekGodsSyncJobTest {

    @Mock
    GreekGodsSyncService syncService;

    @InjectMocks
    GreekGodsSyncJob job;

    @Test
    void runSync_delegatesToSyncService() {
        job.runSync();
        verify(syncService).syncFromUpstream();
    }
}
