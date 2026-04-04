package info.jab.ms.gods.resource;

import static org.assertj.core.api.Assertions.assertThat;

import info.jab.ms.gods.api.model.ProblemDetail;
import info.jab.ms.gods.repository.GreekGodsDataAccessException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class GreekGodsExceptionMapperTest {

    private final GreekGodsExceptionMapper mapper = new GreekGodsExceptionMapper();

    @Test
    void toResponse_returnsProblemDetailsWith500() {
        var cause = new RuntimeException("root");
        var ex = new GreekGodsDataAccessException("read failed", cause);

        Response response = mapper.toResponse(ex);

        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getMediaType().toString()).contains("application/problem+json");
        ProblemDetail body = (ProblemDetail) response.getEntity();
        assertThat(body.getType()).isEqualTo("urn:problem:greek-gods:data-access");
        assertThat(body.getTitle()).isEqualTo("Database access failed");
        assertThat(body.getStatus()).isEqualTo(500);
    }
}
