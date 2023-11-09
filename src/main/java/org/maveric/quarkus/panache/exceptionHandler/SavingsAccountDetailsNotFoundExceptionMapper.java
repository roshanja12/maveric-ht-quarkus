package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SavingsAccountDetailsNotFoundExceptionMapper implements ExceptionMapper<SavingsAccountDetailsNotFoundException> {

    @Override
    public Response toResponse(SavingsAccountDetailsNotFoundException e) {
        ErrorResponse.ErrorMessage errorMessages =

                new ErrorResponse.ErrorMessage("/api/v1/accounts/saving",
                        e.getMessage());
                new ErrorResponse.ErrorMessage(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(errorMessages)).build();
    }
}


