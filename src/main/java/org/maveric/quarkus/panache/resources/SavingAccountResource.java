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
import org.maveric.quarkus.panache.dtos.DraftDetailsRequestDto;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.SavingAccountRequestDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;

@Path("/api/v1/accounts/saving")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account End Points")
public class SavingAccountResource {


    @POST
    @Operation(summary = "This Api creates saving account for customer")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "201", description = "Account Created Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),})
    public Response createAccount( @RequestBody SavingAccountRequestDto savingBankDto) {

        ResponseDto responseDto = new ResponseDto();
        if(responseDto==null)
        {
            responseDto.setStatus("Success");
            responseDto.setCode(201L);
            responseDto.setMessage("Account Created Successfully");
            responseDto.setError(null);
            responseDto.setPath("/api/v1/accounts/saving");
            responseDto.setData(null);
            return Response.status(400).entity(responseDto).build();

        }
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Account Created Successfully");
        responseDto.setError(null);
        responseDto.setPath("/save-account");
        responseDto.setData(null);
        return Response.status(201).entity(responseDto).build();
    }

    @PUT
 @Operation(summary = " This Api for update draft details and status of accounts")
    @APIResponses({ @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Updated Successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                    }),})
    public Response UpdateAccountDetails(@RequestBody UpdateAccountsRequestDto draftDetailsRequestDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Account Updated Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-draft-details");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();
    }


    @GET
    @Operation(summary = "This Api to get all saving account details ")
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
    public Response getSaveAccounts(@QueryParam("page") Long page,
                             @QueryParam("size") Long size,
                             @QueryParam("orderBy") String orderBy,
                             @QueryParam("search") String search
    ) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(200L);
        responseDto.setMessage("Data Rendered Successfully");
        responseDto.setError(null);
        responseDto.setPath("/api/v1/accounts/saving");
        SavingAccountRequestDto savingBankDto = new SavingAccountRequestDto();
        responseDto.setData(savingBankDto);
        return Response.status(200).entity(responseDto).build();
    }

}


