package org.maveric.quarkus.panache.exceptionHandler;


import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
@Dependent
public class ExceptionMapper<I extends RuntimeException> implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        log.info(e +"Error Occured ");
        ErrorResponse.ErrorMessage errorMessages =
                new ErrorResponse.ErrorMessage("path", e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(errorMessages)).build();
    }

}
