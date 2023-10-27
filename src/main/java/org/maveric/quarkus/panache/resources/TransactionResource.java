package org.maveric.quarkus.panache.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.TransactionRequestDto;
import org.maveric.quarkus.panache.model.Transaction;


@Path("/api/v1/accounts/saving")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account Transaction End Points")
public class TransactionResource {


    @PUT
    @Path("/withdraws")
    @Operation(summary = "This Api perform debit operation of a customer ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response withdraw(@RequestBody TransactionRequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Debited Successfully");
        responseDto.setError(null);
        responseDto.setPath("api/v1/saving-account/withdraws");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();

    }
    @PUT
    @Path("/deposits")
    @Operation(summary = "This Api perform credit operation of a customer ")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response deposit(@RequestBody TransactionRequestDto requestDto){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Debited Successfully");
        responseDto.setError(null);
        responseDto.setPath("/api/v1/saving-account/deposits");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();

    }

    @GET
    @Path("/{savingAccountId}/transactions")
    @Operation(summary = "This Api fetch  transactions history of customer account")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Created Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response getTransactionHistoriesByCustomerId(@PathParam("savingAccountId") Long savingAccountId) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Successfully");
        responseDto.setError(null);
        responseDto.setPath("/api/v1/saving-account/{savingAccountId}/transactions");
        Transaction transaction = new Transaction();
        responseDto.setData(transaction);
        return Response.status(200).entity(responseDto).build();
    }
}

