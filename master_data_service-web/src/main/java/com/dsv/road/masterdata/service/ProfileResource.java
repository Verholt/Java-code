package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.profile.ProfileBean;
import com.dsv.road.masterdata.ejb.profile.ProfileKeyBean;
import com.dsv.road.masterdata.security.User;
import com.dsv.road.shared.masterdata.dto.DtoProfile;
import com.dsv.road.shared.masterdata.dto.DtoProfileAttribute;
import com.dsv.road.shared.masterdata.dto.DtoProfileKey;
import com.dsv.road.shared.masterdata.dto.DtoProfileList;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.PROFILES;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RequestScoped
@Path(PROFILES)
@Interceptors(LoggerInterceptor.class)
@Api(value = PROFILES)
public class ProfileResource {

    @EJB
    private ProfileBean profileService;
    @EJB
    private ProfileKeyBean profileKeyService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Get Profile",
            notes = "Returns Profile with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoProfile.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response get(@PathParam("id") long id) {
        DtoProfile profile = profileService.get(id);
        if (profile == null) {
            return notFound();
        } else {
            supplementKeys(profile); //for backward compatibility :(
            return ok(profile).build();
        }
    }

    private void supplementKeys(DtoProfile profile) {
        for (DtoProfileAttribute attr : profile.getAttributes()) {
            attr.setKey(getKey(attr));
        }
    }

    private String getKey(DtoProfileAttribute attr) {
        DtoProfileKey key = profileKeyService.get(attr.getKeyId());
        return (key == null) ? StringUtils.EMPTY : key.getKey();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create Profile",
            notes = "Creates given Profile"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response save(@ApiParam(name = "profile", value = "Profile to be created", required = true) DtoProfile profile) {
        long profileId = profileService.save(profile, getUser());
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(profileId);
        return created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Find Profiles",
            notes = "Searches profiles by given search criteria",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoProfile.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response find(@QueryParam("q") String filter, @QueryParam("limit") @DefaultValue("15") int limit) {
        List<DtoProfile> dtoProfiles = isBlank(filter) ? profileService.findAll() : profileService.find(filter, limit);

        for (DtoProfile dtoProfile : dtoProfiles) {
            supplementKeys(dtoProfile);  //for backward compatibility :(
        }
        return ok(new DtoProfileList(dtoProfiles)).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(
            value = "Delete Profile",
            notes = "Returns Profile with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("id") long id) {
        profileService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}/name")
    @ApiOperation(
            value = "Update ProfileName",
            notes = "Updates name of Profile with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateName(@PathParam("id") Long id,
                               @ApiParam(name = "name", value = "New Name", required = true) String name) {
        profileService.updateName(id, name, getUser());
        return responseOk();
    }

    @PUT
    @Path("{id}/description")
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(
            value = "Update ProfileName",
            notes = "Updates name of Profile with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateDescription(@PathParam("id") Long id,
                                      @ApiParam(name = "description", value = "New Description", required = true) String description) {
        profileService.updateDescription(id, description, getUser());
        return responseOk();
    }

    @POST
    @Path("{id}/attributes")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Add attribute to profile",
            notes = "Add an attribute to profile with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response addAttribute(@PathParam("id") Long id,
                                 @ApiParam(name = "profileAttribute", value = "New profileAttribute", required = true) DtoProfileAttribute profileAttribute) {
        profileService.addAttribute(id, profileAttribute, getUser());
        return responseOk();
    }

    @DELETE
    @Path("{profileId}/attributes/{attrId}")
    @ApiOperation(
            value = "Remove Attribute",
            notes = "Removes attribute with attrId from profile with profileId"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully removed"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response removeAttribute(@PathParam("profileId") Long profileId, @PathParam("attrId") Long attrId) {
        profileService.removeAttribute(profileId, attrId, getUser());
        return Response.noContent().build();
    }

    @HEAD
    @Path("{id}")
    @ApiOperation(
            value = "Profile existence",
            notes = "Checks if Profile with given id is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, Profile exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("id") Long id) {
        if (profileService.exists(id)) {
            return Response.ok().build();
        } else {
            return notFound();
        }
    }

    private Response responseOk() {
        return ok().build();
    }

    private Response notFound() {
        return status(NOT_FOUND).build();
    }

    private User getUser() {
        return new User(WASSecurityUtil.getAuthenticatedUserID());
    }

}
