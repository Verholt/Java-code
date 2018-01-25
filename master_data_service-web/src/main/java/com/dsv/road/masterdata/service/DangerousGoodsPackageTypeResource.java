package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.DangerousGoodsPackageTypeBean;
import com.dsv.road.masterdata.exception.DangerousGoodsPackageTypeException;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsPackageType;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.DANGEROUS_GOODS_PACKAGE_TYPES;

@RequestScoped
@Path(DANGEROUS_GOODS_PACKAGE_TYPES)
@Interceptors(LoggerInterceptor.class)
@Api(value = DANGEROUS_GOODS_PACKAGE_TYPES)
public class DangerousGoodsPackageTypeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DangerousGoodsPackageTypeResource.class);

    @EJB
    private DangerousGoodsPackageTypeBean dtoDangerousGoodsPackageTypeBean;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create DangerousGoodsPackageType",
            notes = "Creates given DangerousGoodsPackageType"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 204, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name = "dtoDangerousGoodsPackageType", value = "DangerousGoodsPackageType to be created", required = true) DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType) {
        if (dtoDangerousGoodsPackageType == null) {
            return Response.notModified().entity("The input cannot be null").build();
        }

        DtoDangerousGoodsPackageType createdDtoDangerousGoodsPackageType;
        try {
            createdDtoDangerousGoodsPackageType = dtoDangerousGoodsPackageTypeBean.create(dtoDangerousGoodsPackageType);
        } catch (DangerousGoodsPackageTypeException e) {
            return errorResponse(e);
        }

        if (createdDtoDangerousGoodsPackageType == null) {
            return Response.notModified("The insertion returned null").build();
        }
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{shortAbbreviation}").build(createdDtoDangerousGoodsPackageType.getShortAbbreviation());
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Read DangerousGoodsPackageType",
            notes = "Returns DangerousGoodsPackageType with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoodsPackageType.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("shortAbbreviation") String shortAbbreviation) {
        LOGGER.debug("Attempting to fetch ", shortAbbreviation);
        DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType = null;
        try {
            dtoDangerousGoodsPackageType = dtoDangerousGoodsPackageTypeBean.read(shortAbbreviation);
        } catch (DangerousGoodsPackageTypeException e) {
            return errorResponse(e);
        }

        if (dtoDangerousGoodsPackageType != null) {
            return Response.ok(dtoDangerousGoodsPackageType).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read All DangerousGoodsPackageType",
            notes = "Returns all DangerousGoodsPackageType",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoodsPackageType.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readAll() {
        LOGGER.debug("Attempting to fetch Dangerous Goods Package Type");
        try {
            List<DtoDangerousGoodsPackageType> dtoDangerousGoodsPackageTypes = dtoDangerousGoodsPackageTypeBean.readAll();
            return Response.ok(dtoDangerousGoodsPackageTypes).build();
        } catch (DangerousGoodsPackageTypeException e) {
            return errorResponse(e);
        }
    }

    @DELETE
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Delete DangerousGoodsPackageType",
            notes = "Deletes DangerousGoodsPackageType with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("shortAbbreviation") String shortAbbreviation) {
        try {
            dtoDangerousGoodsPackageTypeBean.delete(shortAbbreviation);
            return Response.noContent().build();
        } catch (DangerousGoodsPackageTypeException e) {
            return errorResponse(e);
        }
    }

    @PUT
    @Path("{shortAbbreviation}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update DangerousGoodsPackageType",
            notes = "Updates given DangerousGoodsPackageType"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 304, message = "Not modified"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("shortAbbreviation") String shortAbbreviation,
            @ApiParam(name = "dtoDangerousGoodsPackageType", value = "DangerousGoodsPackageType to be updated", required = true) DtoDangerousGoodsPackageType dtoDangerousGoodsPackageType) {
        DtoDangerousGoodsPackageType type;
        try {
            dtoDangerousGoodsPackageType.setShortAbbreviation(shortAbbreviation);
            type = dtoDangerousGoodsPackageTypeBean.update(dtoDangerousGoodsPackageType);
        } catch (DangerousGoodsPackageTypeException e) {
            return errorResponse(e);
        }
        if (type == null) {
            return Response.notModified("The insertion returned null").build();
        }
        return Response.ok().build();
    }


    @HEAD
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "DangerousGoodsPackageType Existence",
            notes = "Checks if DangerousGoodsPackageType with given abbreviation exists"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success. DangerousGoodsPackageType exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("shortAbbreviation") String shortAbbreviation) {
        if (dtoDangerousGoodsPackageTypeBean.exists(shortAbbreviation)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    private Response errorResponse(Throwable error) {
        return RestApplication.errorResponse(error);
    }
}
