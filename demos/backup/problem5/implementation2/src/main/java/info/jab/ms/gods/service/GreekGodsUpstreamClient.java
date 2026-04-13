package info.jab.ms.gods.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Jakarta REST Client aligned with {@code src/main/resources/openapi/my-json-server-oas.yaml}
 * (path {@code /greek}, operation {@code getExternalGreekGods}).
 */
@RegisterRestClient(configKey = "greek-gods-upstream-api")
@Path("/greek")
public interface GreekGodsUpstreamClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getExternalGreekGods();
}
