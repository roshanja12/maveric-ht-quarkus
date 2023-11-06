package org.maveric.quarkus.panache.resources;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.validation.Valid;
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
import org.maveric.quarkus.panache.dtos.SavingsAccountRequestDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.services.SavingsAccountServices;

/* @author meleto sofiya */
@Path("/api/v1/accounts/saving")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account End Points")
public class SavingsAccountResource {

    SavingsAccountServices services;

    public SavingsAccountResource(SavingsAccountServices services) {
        this.services = services;
    }

    @POST
    @Operation(summary = "This Api creates saving account for customer")
    @APIResponses({@APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "201", description = "Account Created Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response createAccount(@RequestBody SavingsAccountRequestDto savingBankDto) {


        ResponseDto responseDto = new ResponseDto();
        if (responseDto == null) {
            responseDto.setStatus("Success");
            responseDto.setMessage("Account Created Successfully");
            responseDto.setError(null);
            responseDto.setPath("/api/v1/accounts/saving");
            responseDto.setData(null);
            return Response.status(400).entity(responseDto).build();

        }
        responseDto.setStatus("Success");
        responseDto.setCode(HttpResponseStatus.OK.code());
        responseDto.setMessage("Account Created Successfully");
        responseDto.setError(null);
        responseDto.setPath("/save-account");
        responseDto.setData(null);
        return Response.status(201).entity(responseDto).build();
    }

    @PUT
    @Operation(summary = " This Api for update draft details and status of accounts")
    @APIResponses({@APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Updated Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),})
    public Response updateAccountsDetails(@RequestBody @Valid UpdateAccountsRequestDto updateAccountsRequestDto) {
        return Response.status(HttpResponseStatus.OK.code()).entity(services.updateAccountsDetails(updateAccountsRequestDto)).build();
    }

    @GET
    @Operation(summary = "This Api to get all saving account details ")
    @APIResponses({@APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Data render successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response getSaveAccounts(@QueryParam("page") Integer page,
                                    @QueryParam("size") Integer size,
                                    @QueryParam("search") String search
    ) {
        return Response.status(HttpResponseStatus.OK.code()).entity(services.getSavingAccount(page, size, search)).build();
    }

    @GET
    @Path("/{accountId}")
    @Operation(summary = "This Api to get saving account details based on accountId")
    @APIResponses({@APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Data render successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response getSavingAccountDetailBasedOnAccountId(@PathParam("accountId") Long accountId

    ) {
        return Response.status(HttpResponseStatus.OK.code()).entity(services.getSavingAccountDetailBasedOnAccountId(accountId)).build();
    }

    @GET
    @Path("/{customerId}")
    @Operation(summary = "This Api to get saving account details based on customerId")
    @APIResponses({@APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Data render successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response getSavingAccountDetailBasedOnCustomerId(@PathParam("customerId") Long customerId

    ) {
        return Response.status(HttpResponseStatus.OK.code()).entity(services.getSavingAccountDetailBasedOnCustomerId(customerId)).build();
    }

}


