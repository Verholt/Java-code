package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.MDGPartyStatusBean;
import com.dsv.road.shared.masterdata.dto.DtoMDGPartyStatus;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.MDG_PARTY_STATUSES;

@RequestScoped
@Path(MDG_PARTY_STATUSES)
@Interceptors(LoggerInterceptor.class)
@Api(value = MDG_PARTY_STATUSES)
public class MDGPartyStatusResource {

    @EJB
    private MDGPartyStatusBean dtoMDGPartyStatusBean;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{statusCode}/{locality}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read MDGPartyStatus",
            notes = "Returns MDGPartyStatus by given search criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoMDGPartyStatus.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("statusCode") String statusCode, @PathParam("locality") String locality) {
        DtoMDGPartyStatus dtoMDGPartyStatus = dtoMDGPartyStatusBean
                .getMDGPartyStatus(statusCode, locality);
        if (dtoMDGPartyStatus != null) {
            return Response.ok(dtoMDGPartyStatus).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create MDGPartyStatus",
            notes = "Creates given MDGPartyStatus"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name = "dtoMDGPartyStatus", value = "MDGPartyStatus to be created", required = true) DtoMDGPartyStatus dtoMDGPartyStatus) {
        if (StringUtils.isBlank(dtoMDGPartyStatus.getLocality()) || StringUtils.isBlank(dtoMDGPartyStatus.getStatusCode())) {
            return errorResponse("Neither locality nor status code can be blank");
        }

        DtoMDGPartyStatus createdDtoMDGPartyStatus = dtoMDGPartyStatusBean
                .createMDGPartyStatus(dtoMDGPartyStatus);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{statusCode}").path("{locality}").build(createdDtoMDGPartyStatus.getStatusCode(), createdDtoMDGPartyStatus.getLocality());
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{statusCode}/{locality}")
    @ApiOperation(
            value = "Delete MDGPartyStatus",
            notes = "Deletes MDGPartyStatus with given parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("statusCode") String statusCode, @PathParam("locality") String locality) {
        boolean success = dtoMDGPartyStatusBean.deleteMDGPartyStatus(statusCode, locality);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{statusCode}/{locality}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update MDGPartyStatus",
            notes = "Updates MDGPartyStatus using given data"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("statusCode") String statusCode, @PathParam("locality") String locality,
            @ApiParam(name = "dtoMDGPartyStatus", value = "MDGPartyStatus to be updated", required = true) DtoMDGPartyStatus dtoMDGPartyStatus) {
        dtoMDGPartyStatus.setStatusCode(statusCode);
        dtoMDGPartyStatus.setLocality(locality);
        dtoMDGPartyStatusBean.updateMDGPartyStatus(dtoMDGPartyStatus);
        return Response.ok().build();
    }

    private Response errorResponse(String error) {
        return RestApplication.errorResponse(error);
    }
}