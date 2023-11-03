package org.maveric.quarkus.panache.exception;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.maveric.quarkus.panache.dtos.ResponseDto;

import java.time.Instant;
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<SavingAccountIdNotFound> {
    @Override
    public Response toResponse(SavingAccountIdNotFound exception) {
        ResponseDto responseDto=new ResponseDto("Error","Resource Not Found",
                404L,exception.getMessage(),"/api/v1/saving-account/{savingAccountId}/transactions",
                Instant.now(),null);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(responseDto)
                .build();
    }
}
