package org.maveric.quarkus.panache.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.maveric.quarkus.panache.dtos.DraftDetailsRequestDto;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.SavingAccountRequestDto;

@Path("account/saving-account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account End Points")
public class SavingAccountResource {


    @POST
    //@Operation(summary = "Creates Customer Saving Account")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Resources not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred,please"),
            @APIResponse(responseCode = "201", description = "Account Created Successfully"),})
    public Response createAccount( @RequestBody SavingAccountRequestDto savingBankDto) {

        ResponseDto responseDto = new ResponseDto();
        if(responseDto==null)
        {
            responseDto.setStatus("Success");
            responseDto.setCode(201L);
            responseDto.setMessage("Account Created Successfully");
            responseDto.setError(null);
            responseDto.setPath("/save-account");
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
    @Path("/update-draft-details")
  //  @Operation(summary = "Update Draft Details")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Updated Draft Details"),})
    public Response UpdateDraftDetails(@RequestBody DraftDetailsRequestDto draftDetailsRequestDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Account Updated Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-draft-details");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();
    }


    @PUT
    @Path("/update-account-status/{accountId}/{statusValue}")
  //  @Operation(summary = "Update Saving Account Status")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Status Updated Successfully"),})
    public Response UpdateAccountStatus(@PathParam("accountId") Long accountId,
                                       @PathParam("statusValue") String status
                                       ) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Account Status Updated Successfully");
        responseDto.setError(null);
        responseDto.setPath("/update-account-status");
        responseDto.setData(null);
        return Response.status(200).entity(responseDto).build();
    }


    @GET
    @Path("/get-account-details/{page}/{size}/{search}")
  //  @Operation(summary = "Get Saving Account Details With Paging")
    @APIResponses({  @APIResponse(responseCode = "400", description = "Bad Request: The request is invalid"),
            @APIResponse(responseCode = "401", description = "Unauthorized request"),
            @APIResponse(responseCode = "404", description = "Api not found"),
            @APIResponse(responseCode = "500", description = "Internal Server Error: An unexpected error occurred"),
            @APIResponse(responseCode = "200", description = "Account Details Rendered Successfully"),})
    public Response getSaveAccounts(@PathParam("page") Long page,
                             @PathParam("size") Long size,
                             @PathParam("orderBy") String orderBy,
                             @PathParam("search") String search
    ) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus("Success");
        responseDto.setCode(201L);
        responseDto.setMessage("Data Rendered Successfully");
        responseDto.setError(null);
        responseDto.setPath("/get-account-details");
        SavingAccountRequestDto savingBankDto = new SavingAccountRequestDto();
        responseDto.setData(savingBankDto);
        return Response.status(200).entity(responseDto).build();
    }

}


