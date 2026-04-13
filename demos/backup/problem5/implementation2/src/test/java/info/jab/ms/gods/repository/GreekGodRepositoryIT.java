package info.jab.ms.gods.repository;

import static org.assertj.core.api.Assertions.assertThat;

import info.jab.ms.gods.testsupport.PostgresTestResource;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(value = PostgresTestResource.class, restrictToAnnotatedClass = true)
class GreekGodRepositoryIT {

    @Inject
    GreekGodRepository repository;

    @Inject
    AgroalDataSource dataSource;

    @BeforeEach
    void truncate() throws Exception {
        try (Connection c = dataSource.getConnection();
                Statement s = c.createStatement()) {
            s.executeUpdate("TRUNCATE TABLE greek_god RESTART IDENTITY");
        }
    }

    @Test
    @Tag("persistence")
    void findAllNamesOrdered_whenEmpty_returnsEmptyList() {
        assertThat(repository.findAllNamesOrdered()).isEmpty();
    }

    @Test
    @Tag("persistence")
    void upsertNamesBatch_thenFindAllNamesOrdered_returnsSortedDistinctRows() {
        int inserted = repository.upsertNamesBatch(List.of("Zeus", "Hera"));
        assertThat(inserted).isEqualTo(2);
        assertThat(repository.findAllNamesOrdered()).containsExactly("Hera", "Zeus");
    }

    @Test
    @Tag("persistence")
    void upsertNamesBatch_onConflict_returnsZeroAdditionalInserts() {
        assertThat(repository.upsertNamesBatch(List.of("Athena"))).isEqualTo(1);
        assertThat(repository.upsertNamesBatch(List.of("Athena"))).isZero();
        assertThat(repository.findAllNamesOrdered()).containsExactly("Athena");
    }
}
