package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.PackageTypeBean;
import com.dsv.road.shared.masterdata.dto.DtoPackageType;
import com.dsv.road.shared.masterdata.exception.ValidationException;
import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.rest.exception.helper.ValidationHelper;
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

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.PACKAGE_TYPES;

@RequestScoped
@Path(PACKAGE_TYPES)
@Interceptors(LoggerInterceptor.class)
@Api(value = PACKAGE_TYPES)
public class PackageTypeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageTypeResource.class);

    @EJB
    private PackageTypeBean packageTypeBean;

    @Inject
    private ValidationHelper validationHelper;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read PackageTypes",
            notes = "Rads all PackageTypes",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted", response = DtoPackageType.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readAll() {
        List<DtoPackageType> dtoPackageTypes = packageTypeBean.getPackageTypes();
        return Response.ok(dtoPackageTypes).build();
    }

    @GET
    @Path("{shortAbbreviation}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read PackageType",
            notes = "Reads PackageType with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted", response = DtoPackageType.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("shortAbbreviation") String shortAbbreviation) {
        LOGGER.debug("Fetching PackageType for shortAbbreviation: " + shortAbbreviation);

        DtoPackageType dtoPackageType = packageTypeBean.getPackageType(shortAbbreviation);
        if (dtoPackageType != null) {
            return Response.ok(dtoPackageType).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create PackageType",
            notes = "Creates PackageType with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(@ApiParam(name = "dtoPackageType", value = "PackageType to be created", required = true) DtoPackageType dtoPackageType) {
        validationHelper.validateBean(dtoPackageType).throwOnErrors();
        dtoPackageType.setCreatedBy(WASSecurityUtil.getAuthenticatedUserID());
        DtoPackageType createdPackageType = packageTypeBean.createPackageType(dtoPackageType);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{shortAbbreviation}").build(createdPackageType.getShortAbbreviation());
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Delete PackageType",
            notes = "Deletes PackageType with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("shortAbbreviation") String shortAbbreviation) {
        boolean success = packageTypeBean.deletePackageType(shortAbbreviation);
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
            value = "Update PackageType",
            notes = "Updates PackageType using given data"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = DtoPackageType.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("shortAbbreviation") String shortAbbreviation, @ApiParam(name = "dtoPackageType", value = "PackageType to be updated", required = true) DtoPackageType dtoPackageType) throws ValidationException {
        validationHelper.validateBean(dtoPackageType).throwOnErrors();
        dtoPackageType.setShortAbbreviation(shortAbbreviation);
        dtoPackageType.setUpdatedBy(WASSecurityUtil.getAuthenticatedUserID());
        DtoPackageType updatedPackageType = packageTypeBean.updatePackageType(dtoPackageType);
        return Response.ok(updatedPackageType).build();
    }

    @HEAD
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "PackageType existence",
            notes = "Checks if PackageType with given shortAbbreviation is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, PackageType exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("shortAbbreviation") String shortAbbreviation) {
        if (packageTypeBean.exists(shortAbbreviation)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
