package info.jab.ms.gods.service;

import info.jab.ms.gods.repository.GreekGodRepository;
import info.jab.ms.gods.repository.GreekGodsDataAccessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GreekGodsSyncService {

    private static final Logger LOG = Logger.getLogger(GreekGodsSyncService.class);

    @Inject
    @RestClient
    GreekGodsUpstreamClient upstreamClient;

    @Inject
    GreekGodRepository repository;

    public void syncFromUpstream() {
        try {
            List<String> names = upstreamClient.getExternalGreekGods();
            if (names == null || names.isEmpty()) {
                LOG.info("Upstream returned no Greek god names; repository unchanged");
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
                LOG.info("Upstream returned no usable Greek god names after trimming; repository unchanged");
                return;
            }
            try {
                int inserted = repository.upsertNamesBatch(List.copyOf(distinctNames));
                LOG.infof(
                        "Greek gods sync finished: upstream count=%d, distinct names=%d, rows inserted (new)=%d",
                        names.size(),
                        distinctNames.size(),
                        inserted);
            } catch (GreekGodsDataAccessException ex) {
                LOG.warn("Greek gods sync batch upsert failed", ex);
            }
        } catch (Exception ex) {
            LOG.warnf("Greek gods upstream sync failed; will retry on next schedule tick: %s", ex.getMessage());
            LOG.debug("Greek gods sync failure detail", ex);
        }
    }
}
