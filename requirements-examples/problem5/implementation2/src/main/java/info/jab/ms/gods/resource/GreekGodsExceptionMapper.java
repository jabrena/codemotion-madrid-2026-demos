package info.jab.ms.gods.resource;

import info.jab.ms.gods.api.model.ProblemDetail;
import info.jab.ms.gods.repository.GreekGodsDataAccessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GreekGodsExceptionMapper implements ExceptionMapper<GreekGodsDataAccessException> {

    private static final Logger LOG = Logger.getLogger(GreekGodsExceptionMapper.class);

    private static final String PROBLEM_TYPE = "urn:problem:greek-gods:data-access";

    @Override
    public Response toResponse(GreekGodsDataAccessException exception) {
        LOG.error("Greek gods data access failure on read path", exception);
        ProblemDetail body = new ProblemDetail(PROBLEM_TYPE, "Database access failed", 500);
        return Response.status(500)
                .type("application/problem+json")
                .entity(body)
                .build();
    }
}
