package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.profile.ProfileKeyBean;
import com.dsv.road.masterdata.security.User;
import com.dsv.road.shared.masterdata.dto.DtoProfileKey;
import com.dsv.road.shared.masterdata.dto.DtoProfileKeyList;
import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.rest.exception.helper.ValidationHelper;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.PROFILE_KEYS;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequestScoped
@Path(PROFILE_KEYS)
@Interceptors(LoggerInterceptor.class)
@Api(value = PROFILE_KEYS)
public class ProfileKeyResource {

    @EJB
    private ProfileKeyBean profileKeyBean;

    @Inject
    private ValidationHelper validationHelper;


    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Get ProfileKey",
            notes = "Returns ProfileKey with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoProfileKey.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response get(@PathParam("id") long id) {
        DtoProfileKey profileKey = profileKeyBean.get(id);
        return (profileKey == null) ? notFound() : ok(profileKey).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create ProfileKey",
            notes = "Creates given ProfileKey"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response save(@ApiParam(name = "profileKey", value = "Profile key to be created", required = true) DtoProfileKey profileKey) {
        long keyId = profileKeyBean.save(profileKey, getUser());
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(keyId);
        return created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update ProfileKey",
            notes = "Updates ProfileKey using given data"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = DtoProfileKey.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("id") long id,
                           @ApiParam(name = "profileKey", value = "Profile key to be updated", required = true) DtoProfileKey profileKey) {
        profileKey.setId(id);
        validationHelper.validateBean(profileKey).throwOnErrors();
        profileKeyBean.update(profileKey, getUser());
        return ok().build();
    }

    private User getUser() {
        return new User(WASSecurityUtil.getAuthenticatedUserID());
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(
            value = "Delete ProfileKey",
            notes = "Removes ProfileKey with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("id") long id) {
        profileKeyBean.delete(id);
        return noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find ProfileKeys",
            notes = "Finds ProfileKeys by given search criteria",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoProfileKey.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response find(@QueryParam("q") String filter, @QueryParam("limit") @DefaultValue("15") int limit) {
        List<DtoProfileKey> dtoProfileKeys = isBlank(filter) ? profileKeyBean.findAll() : profileKeyBean.find(filter, limit);
        return ok(new DtoProfileKeyList(dtoProfileKeys)).build();
    }

    @HEAD
    @Path("{id}")
    @ApiOperation(
            value = "ProfileKey existence",
            notes = "Checks if ProfileKey with given id is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, ProfileKey exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("id") Long id) {
        if (profileKeyBean.exists(id)) {
            return Response.ok().build();
        } else {
            return notFound();
        }
    }

    private Response notFound() {
        return status(NOT_FOUND).build();
    }
}
