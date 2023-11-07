package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SavingDetailsNotFoundExceptionMapper implements ExceptionMapper<SavingDetailsNotFoundException> {

    @Override
    public Response toResponse(SavingDetailsNotFoundException e) {
        ErrorResponse.ErrorMessage errorMessages =
                new ErrorResponse.ErrorMessage("/a/b", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(errorMessages)).build();
    }
}


