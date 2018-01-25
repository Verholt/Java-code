package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.RoleBean;
import com.dsv.road.shared.masterdata.dto.DtoRole;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.ROLES;

@RequestScoped
@Path(ROLES)
@Interceptors(LoggerInterceptor.class)
@Api(value = ROLES)
public class RoleResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleResource.class);

	@EJB
	private RoleBean roleBean;

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Role Search",
			notes = "Searches for Role using given search criteria"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response search(@QueryParam("q") @DefaultValue("") String filter, @QueryParam("limit") @DefaultValue("15") int limit) {
		List<DtoRole> dtoRoleList = roleBean.searchRoles(filter, limit);
		return Response.ok(dtoRoleList).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Create Role",
			notes = "Create given Role"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 204, message = "Insertion returned null"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response create(@ApiParam(name = "dtoRole", value = "Role to be created", required = true) final DtoRole dtoRole) {
		dtoRole.setCreatedBy(WASSecurityUtil.getAuthenticatedUserID());
		DtoRole createdDtoRole = roleBean.createRole(dtoRole);
		if (createdDtoRole == null) {
			return Response.notModified("The insertion returned null").build();
		}
		UriBuilder ub = uriInfo.getRequestUriBuilder();
		URI unRoleURI = ub.path("{id}").build(createdDtoRole.getId());
		return Response.created(unRoleURI).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@ApiOperation(
			value = "Read Role",
			notes = "Returns Role with given id"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = DtoRole.class),
			@ApiResponse(code = 404, message = "Not found", response = DtoRole.class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response read(@PathParam("id") Long id) {
		DtoRole dtoRole = roleBean.readRole(id);
		if (dtoRole == null) {
			LOGGER.debug("Could not find any roles");
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(dtoRole).build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Update Role",
			notes = "Updates Role using given data"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated",response = DtoRole.class),
			@ApiResponse(code = 304, message = "Insertion returned null"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response update(@PathParam("id") long id,
						   @ApiParam(name = "dtoRole", value = "Role to be updated", required = true) DtoRole dtoRole) {
		dtoRole.setId(id);
		DtoRole role = roleBean.updateRole(dtoRole);
		if (role == null) {
			return Response.notModified("The insertion returned null").build();
		}
		return Response.ok(role).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@ApiOperation(
			value = "Delete Role",
			notes = "Removes Role with given id"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response delete(@PathParam("id") long id) {
		roleBean.deleteRole(id);
		return Response.noContent().build();
	}

	@HEAD
	@Path("{id}")
	@ApiOperation(
			value = "Role existence",
			notes = "Checks if Role with given id is present"
	)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success, Role exists"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	public Response exists(@PathParam("id") Long id){
		if(roleBean.exists(id)){
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
