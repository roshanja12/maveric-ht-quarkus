package org.maveric.quarkus.panache.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.model.Transaction;


@Path("/saving-account/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account Transaction End Points")
public class TransactionResource {


    @POST
    @Path("/withDrawAmount/{savingAccountId}")
    //@Operation(summary = "Withdraw Amount Based On Saving Account Id ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Debited Successfully"),})
    public Response withdraw(@PathParam("savingAccountId") Long accountId,
                             @PathParam("amount") String amount){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Debited Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-account-status");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();

    }
    @POST
    @Path("/deposit/{savingAccountId}")
   // @Operation(summary = "Deposit Amount Based On Saving Account Id ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Credited Successfully"),})
    public Response deposit(@PathParam("savingAccountId") Long accountId,
                            @PathParam("amount") String amount){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Debited Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-account-status");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();

    }

    @GET
    @Path("/get-transaction/{savingAccountId}")
   // @Operation(summary = "Fetch History Of Transaction Based On Saving Account Id")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Transaction Details Rendered Successfully"),})
    public Response getTransactionHistoriesByCustomerId(@PathParam("savingAccountId") Long savingAccountId) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Debited Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-account-status");
        Transaction transaction = new Transaction();
        responseDto.setData(transaction);
        return Response.status(200).entity(responseDto).build();
    }
}

