package fi.helsinki.koulutustarjonta.resource;

import fi.helsinki.koulutustarjonta.dao.exception.ResourceNotFound;
import fi.helsinki.koulutustarjonta.dto.ErrorPayloadDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Hannu Lyytikainen
 */
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFound> {

    @Override
    public Response toResponse(ResourceNotFound exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorPayloadDTO(exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
