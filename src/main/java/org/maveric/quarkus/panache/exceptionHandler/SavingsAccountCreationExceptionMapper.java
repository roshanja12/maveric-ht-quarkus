package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SavingsAccountCreationExceptionMapper implements ExceptionMapper<SavingsAccountCreationException> {

    @Override
    public Response toResponse(SavingsAccountCreationException e) {
        ErrorResponse.ErrorMessage errorMessages =
                new ErrorResponse.ErrorMessage(e.getMessage());
        ErrorResponse response = new ErrorResponse(errorMessages);
        response.setCode(Response.Status.BAD_REQUEST.getStatusCode());

        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }    }