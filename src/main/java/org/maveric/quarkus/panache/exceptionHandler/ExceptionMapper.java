package org.maveric.quarkus.panache.exceptionHandler;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        ErrorResponse.ErrorMessage errorMessages =
                new ErrorResponse.ErrorMessage( e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(errorMessages)).build();
    }

}
