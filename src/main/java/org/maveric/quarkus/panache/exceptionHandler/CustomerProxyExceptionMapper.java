package org.maveric.quarkus.panache.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerProxyExceptionMapper  implements ExceptionMapper<CustomerProxyException> {
    @Override
    public Response toResponse(CustomerProxyException e) {
        ErrorResponse.ErrorMessage errorMessages =
                new ErrorResponse.ErrorMessage(e.getMessage());
        ErrorResponse response = new ErrorResponse(errorMessages);
        response.setCode(Response.Status.NOT_FOUND.getStatusCode());

        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}