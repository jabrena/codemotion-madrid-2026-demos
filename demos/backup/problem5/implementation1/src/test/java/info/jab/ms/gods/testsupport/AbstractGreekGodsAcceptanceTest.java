package info.jab.ms.gods.testsupport;

/**
 * Optional base for acceptance tests ({@code *AT}) that use the same full-stack setup as
 * integration tests: random port, {@code test} profile, and PostgreSQL via Testcontainers.
 */
public abstract class AbstractGreekGodsAcceptanceTest extends AbstractGreekGodsPostgresIntegrationTest {
}
