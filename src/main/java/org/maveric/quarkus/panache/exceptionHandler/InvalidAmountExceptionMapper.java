package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidAmountExceptionMapper implements ExceptionMapper<InvalidAmountException> {
    @Override
    public Response toResponse(InvalidAmountException exception) {
        ErrorResponse.ErrorMessage errorMessage=new ErrorResponse.ErrorMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(errorMessage)).build();
    }
}
