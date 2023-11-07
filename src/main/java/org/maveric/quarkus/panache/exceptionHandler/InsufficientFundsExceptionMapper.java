package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientFundsExceptionMapper implements ExceptionMapper<InsufficientFundsException> {
    @Override
    public Response toResponse(InsufficientFundsException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Insufficient Funds:"+exception.getMessage()).build();
    }
}
