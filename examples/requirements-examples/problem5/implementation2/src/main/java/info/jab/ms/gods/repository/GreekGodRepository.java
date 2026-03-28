package info.jab.ms.gods.repository;

import info.jab.ms.gods.model.GreekGod;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GreekGodRepository implements PanacheRepository<GreekGod> {

    private static final Logger LOG = Logger.getLogger(GreekGodRepository.class);

    @Transactional
    public List<String> findAllNamesOrdered() {
        try {
            List<String> names = listAll(Sort.ascending("name")).stream().map(g -> g.name).toList();
            LOG.debugf("Read %d Greek god name(s) from database", names.size());
            return names;
        } catch (PersistenceException e) {
            throw new GreekGodsDataAccessException("Failed to read Greek god names", e);
        }
    }

    /**
     * Batch upsert distinct trimmed names. Returns the sum of per-row update counts from the driver
     * (new rows typically contribute 1; conflicts contribute 0).
     */
    @Transactional
    public int upsertNamesBatch(List<String> distinctTrimmedNames) {
        if (distinctTrimmedNames == null || distinctTrimmedNames.isEmpty()) {
            return 0;
        }
        try {
            return getEntityManager().unwrap(Session.class).doReturningWork(connection -> {
                try (PreparedStatement ps = connection.prepareStatement(
                        """
                        INSERT INTO greek_god (name) VALUES (?)
                        ON CONFLICT (name) DO NOTHING
                        """)) {
                    for (String name : distinctTrimmedNames) {
                        ps.setString(1, name);
                        ps.addBatch();
                    }
                    int[] counts = ps.executeBatch();
                    int inserted = 0;
                    for (int c : counts) {
                        if (c >= 0) {
                            inserted += c;
                        }
                    }
                    return inserted;
                } catch (SQLException e) {
                    throw new PersistenceException(e);
                }
            });
        } catch (PersistenceException e) {
            throw new GreekGodsDataAccessException("Failed to batch upsert Greek god names", e);
        }
    }
}
