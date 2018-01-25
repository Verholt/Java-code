package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.ReferenceTypeBean;
import com.dsv.road.shared.masterdata.dto.DtoReferenceType;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.REFERENCE_TYPES;

@RequestScoped
@Path(REFERENCE_TYPES)
@Interceptors(LoggerInterceptor.class)
@Api(value = REFERENCE_TYPES)
public class ReferenceTypeResource {

    private static final String DEFAULT_REFERENCES_FILTER = "default";

    @Context
    private UriInfo uriInfo;
    @EJB
    private ReferenceTypeBean referenceTypeBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Search ReferenceType",
            notes = "Searches for ReferenceType using given filter. When 'default' is provided, DefaultReferenceTypes will be returned",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoReferenceType.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response search(@QueryParam("q") @DefaultValue("") String filter) {
        List<DtoReferenceType> dtoPackageTypes;
        if (DEFAULT_REFERENCES_FILTER.equals(filter)) {
            dtoPackageTypes = referenceTypeBean.getDefaultReferenceTypes();
        } else {
            dtoPackageTypes = referenceTypeBean.getReferenceTypes();
        }
        return Response.ok(dtoPackageTypes).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Read ReferenceType",
            notes = "Returns ReferenceType with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoReferenceType.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("id") Long id) {
        DtoReferenceType referenceType = referenceTypeBean.getReferenceType(id);
        if (referenceType == null) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(referenceType).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create ReferenceType",
            notes = "Creates given ReferenceType"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name = "dtoReferenceType", value = "ReferenceType to be created", required = true) DtoReferenceType dtoReferenceType) {
        DtoReferenceType createdReferenceType = referenceTypeBean.createReferenceType(dtoReferenceType);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(createdReferenceType.getId());
        return Response.created(uri).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Delete ReferenceType",
            notes = "Removes ReferenceType with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("id") long id) {
        boolean success = referenceTypeBean.deleteReferenceType(id);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update ReferenceType",
            notes = "Updates ReferenceType using given data"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = DtoReferenceType.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("id") Long id,
            @ApiParam(name = "dtoReferenceType", value = "ReferenceType to be updated", required = true) DtoReferenceType dtoPackageType) {
        dtoPackageType.setId(id);
        DtoReferenceType updatedReferenceType = referenceTypeBean.updateReferenceType(dtoPackageType);
        return Response.ok(updatedReferenceType).build();
    }

    @HEAD
    @Path("{id}")
    @ApiOperation(
            value = "ReferenceType existence",
            notes = "Checks if ReferenceType with given id is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, ReferenceType exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("id") Long id) {
        if (referenceTypeBean.exists(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
