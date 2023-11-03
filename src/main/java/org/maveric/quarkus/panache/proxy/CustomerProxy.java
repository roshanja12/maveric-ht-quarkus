package org.maveric.quarkus.panache.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "customer.proxy")
public interface CustomerProxy {
    @GET
    @Path("/api/v1/customers/searchByCustomerId")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCustomerByCustomerId(@QueryParam("customerId") Long customerId);

}
