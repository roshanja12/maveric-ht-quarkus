package org.maveric.quarkus.panache.resources;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.maveric.quarkus.panache.dtos.ResponseDto;
import org.maveric.quarkus.panache.dtos.SavingAccountRequestDto;
import org.maveric.quarkus.panache.dtos.SavingAccountResponseDto;
import org.maveric.quarkus.panache.dtos.UpdateAccountsRequestDto;
import org.maveric.quarkus.panache.services.SavingAccountServices;

import java.time.Instant;

/* @author meleto sofiya */
@Path("/api/v1/accounts/saving")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Saving Account End Points")
@Slf4j
public class SavingAccountResource {

    @Inject
    SavingAccountServices services;

    public SavingAccountResource(SavingAccountServices services) {
        this.services = services;
    }

    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @POST
    @Operation(summary = "This Api creates saving account for customer")
    @Transactional

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
    public Response createAccount(@RestForm("image") FileUpload file, @RestForm @PartType(MediaType.APPLICATION_JSON) @Valid SavingAccountRequestDto savingAccountRequestDto) throws Exception {
        log.info("Inside createAccount api");
        SavingAccountResponseDto accountResponse = services.createAccount(file, savingAccountRequestDto);
        ResponseDto responseDto = ResponseDto.builder()
                .status("success")
                .message("Savings Account Created Successfully")
                .code(201)
                .error(null)
                .path("/api/v1/accounts/saving")
                .timeStamp(Instant.now())
                .data(accountResponse)
                .build();
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
            @APIResponse(responseCode = "200", description = "Account Created Successfully",
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

}


