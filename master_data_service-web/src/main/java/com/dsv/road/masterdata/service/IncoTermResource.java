package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.IncoTermBean;
import com.dsv.road.shared.masterdata.dto.DtoIncoTerm;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.*;

@RequestScoped
@Path(INCO_TERMS)
@Interceptors(LoggerInterceptor.class)
@Api(value = INCO_TERMS)
public class IncoTermResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncoTermResource.class);

    @EJB
    private IncoTermBean incoTermBean;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read Incoterms",
            notes = "Returns all incoterms",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response=DtoIncoTerm.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readAll() {
        LOGGER.debug("Attempting to fetch incoterms");
        List<DtoIncoTerm> dtoIncoTermList = incoTermBean.getIncoterms();
        if (dtoIncoTermList == null) {
            LOGGER.debug("Could not find any incoterms");
            return errorResponse("IncoTermsList is empty");
        } else {
            return Response.ok(dtoIncoTermList).build();
        }
    }

    @GET
    @Path("{shortAbbreviation}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read Incoterm",
            notes = "Returns incoterm with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response=DtoIncoTerm.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("shortAbbreviation") String shortAbbreviation) {
        LOGGER.debug("Fetching IncoTerm for shortAbbreviation: " + shortAbbreviation);
        DtoIncoTerm dtoIncoTerm = incoTermBean.getIncoTerm(shortAbbreviation);
        if (dtoIncoTerm != null) {
            return Response.ok(dtoIncoTerm).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create Incoterm",
            notes = "Create given incoterm"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response=URI.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name="dtoIcoTerm", value = "Incoterm to be created", required = true)DtoIncoTerm dtoIncoTerm) {
        dtoIncoTerm.setCreatedBy(WASSecurityUtil.getAuthenticatedUserID());
        DtoIncoTerm createdIncoTerm = incoTermBean.createIncoTerm(dtoIncoTerm);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri= requestUriBuilder.path("{shortAbbreviation}").build(createdIncoTerm.getShortAbbreviation());
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Delete Incoterm",
            notes = "Deletes incoterm with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("shortAbbreviation") String shortAbbreviation) {
        boolean success = incoTermBean.deleteIncoTerm(shortAbbreviation);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{shortAbbreviation}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update Incoterm",
            notes = "Updates incoterm with given data"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update( @PathParam("shortAbbreviation") String shortAbbreviation,
            @ApiParam(name="dtoIncoTerm", value="Incoterm to be updated", required=true) DtoIncoTerm dtoIncoTerm) {
        dtoIncoTerm.setUpdatedBy(WASSecurityUtil.getAuthenticatedUserID());
        dtoIncoTerm.setShortAbbreviation(shortAbbreviation);
        DtoIncoTerm updatedIncoTerm = incoTermBean.updateIncoTerm(dtoIncoTerm);
        return Response.ok(updatedIncoTerm).build();
    }

    @HEAD
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Incoterm existence",
            notes = "Check if incoterm with given shortAbbreviation is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, incoterm exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("shortAbbreviation")  String shortAbbreviation){
        if(incoTermBean.exists(shortAbbreviation)){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private Response errorResponse(String error) {
        return RestApplication.errorResponse(error);
    }
}
