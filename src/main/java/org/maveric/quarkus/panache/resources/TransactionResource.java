package org.maveric.quarkus.panache.resources;


import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;
import org.maveric.quarkus.panache.dtos.TransactionResponseDto;
import org.maveric.quarkus.panache.model.SavingAccount;
import org.maveric.quarkus.panache.services.TransactionService;
import java.util.List;

@Path("/api/v1/accounts/saving")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account Transaction End Points")
@Slf4j
public class TransactionResource {

    @Inject
    private TransactionService transactionService;


    @PUT
    @Path("/withdraws")
    @Operation(summary = "This Api perform debit operation of a customer ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response withdraw(@RequestBody TransactionRequestDto requestDto){
        log.info("1111111"+requestDto.toString());
       transactionService.withdraw(requestDto);
        return Response.status(HttpResponseStatus.OK.code()).entity("Withdraw Successful").build();
    }
    @PUT
    @Path("/deposits")
    @Operation(summary = "This Api perform credit operation of a customer ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response deposit(@RequestBody TransactionRequestDto requestDto){
        transactionService.deposit(requestDto);
        return Response.status(HttpResponseStatus.OK.code()).entity("Deposit Successful").build();
    }
    @GET
    @Path("/{savingsAccountId}/transactions")
    @Operation(summary = "This Api fetch  transactions history of customer account")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response getTransactionHistories(@PathParam("savingsAccountId") Long savingsAccountId,
                                            @QueryParam("pageNumber") int pageNumber,
                                            @QueryParam("pageSize") int pageSize) {

        List<TransactionResponseDto> transactionResponseDto=transactionService.getTransactions(savingsAccountId,pageNumber,pageSize);
        return Response.status(HttpResponseStatus.OK.code()).entity(transactionResponseDto).build();
    }

}

