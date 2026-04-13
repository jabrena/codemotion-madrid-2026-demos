package info.jab.ms.gods.resource;

import info.jab.ms.gods.api.ApiApi;
import info.jab.ms.gods.repository.GreekGodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implements the contract from {@code src/main/resources/openapi/greekController-oas.yaml} (generated {@link ApiApi}).
 */
@ApplicationScoped
public class GreekGodsResource implements ApiApi {

    @Inject
    GreekGodRepository repository;

    @Override
    public List<String> getGreekGods() {
        return repository.findAllNamesOrdered();
    }
}
