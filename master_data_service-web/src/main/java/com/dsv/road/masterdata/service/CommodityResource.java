package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.CommodityBean;
import com.dsv.road.shared.masterdata.dto.DtoHsCode;
import com.dsv.road.shared.masterdata.dto.DtoHsCodeText;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.*;

@RequestScoped
@Path(COMMODITIES)
@Interceptors(LoggerInterceptor.class)
@Api(value = COMMODITIES)
public class CommodityResource {

    @EJB
    private CommodityBean commodityBean;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(HSCODES)
    @ApiOperation(
            value = "HsCodes",
            notes = "Returns all hsCodes",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoHsCode.class),
            @ApiResponse(code = 500, message = "Internal server error")

    })
    public Response readAllHsCode() {
        List<DtoHsCode> hsCodes = commodityBean.getAllHsCodes();
        if (hsCodes != null) {
            return Response.ok(hsCodes).build();
        }
        return Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(HSCODES + "/{id}")
    @ApiOperation(
            value = "HsCode",
            notes = "Returns hsCode by given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoHsCode.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")

    })
    public Response readHsCode(@PathParam("id") long id) {
        DtoHsCode dtohsCode = commodityBean.getHsCode(id);
        if (dtohsCode == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dtohsCode).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(HSCODES)
    @ApiOperation(
            value = "Create HsCode",
            notes = "Creates given HsCode"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "HsCode successfully created", response = Long.class),
            @ApiResponse(code = 400, message = "No input specified"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response createHsCode(@ApiParam(name = "dtoHsCode", value = "dtoHsCode to be created", required = true) DtoHsCode dtohsCode) {
        DtoHsCode insertedDtoHsCode = commodityBean.insertHsCode(dtohsCode);

        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(insertedDtoHsCode.getId());
        return Response.created(uri).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(HSCODES + "/{id}")
    @ApiOperation(
            value = "Update HsCode",
            notes = "Updates HsCode with given id using data from second Parameter"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "HsCode updated"),
            @ApiResponse(code = 404, message = "No input specified"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateHsCode(@PathParam("id") long id,
                                 @ApiParam(name = "dtoHsCode", value = "DtoHsCode which override HsCode with given id", required = true) DtoHsCode dtoHsCode) {
        commodityBean.updateHsCode(id, dtoHsCode);
        return Response.ok().build();
    }

    @DELETE
    @Path(HSCODES + "/{id}")
    @ApiOperation(
            value = "Delete HsCode",
            notes = "Delete HsCode with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteHsCode(@PathParam("id") long id) {
        commodityBean.deleteHsCode(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(HSCODE_TEXTS + "/{id}")
    @ApiOperation(
            value = "Read HsCodeText",
            notes = "Returns HsCodeText with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readHsCodeText(@PathParam("id") long id) {
        DtoHsCodeText dtoHsCodeText = commodityBean.getHsCodeText(id);
        if (dtoHsCodeText == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dtoHsCodeText).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(HSCODE_TEXTS)
    @ApiOperation(
            value = "Create HsCodeText",
            notes = "Creates given HsCodeText"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = Long.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response createHsCodeText(@ApiParam(name = "dtoHsCodeText", value = "HsCodeText to be created", required = true) DtoHsCodeText dtohsCodeText) {
        DtoHsCodeText insertedDtoHsCodeText = commodityBean.insertHsCodeText(dtohsCodeText);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(insertedDtoHsCodeText.getId());
        return Response.created(uri).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(HSCODE_TEXTS + "/{id}")
    @ApiOperation(
            value = "Update HsCodeText",
            notes = "Updates HsCodeText with given id using data from second parameter"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateHsCodeText(@PathParam("id") long id, DtoHsCodeText dtohsCodeText) {
        commodityBean.updateHsCodeText(id, dtohsCodeText);
        return Response.ok().build();
    }

    @DELETE
    @Path(HSCODE_TEXTS + "/{id}")
    @ApiOperation(
            value = "Delete HsCodeText",
            notes = "Delete HsCodeText with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteHsCodeText(@PathParam("id") long id) {
        commodityBean.deleteHsCodeText(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(HSCODE_TEXTS)
    @ApiOperation(
            value = "Search HsCodeText",
            notes = "Searching HsCodeText with given search criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            //@ApiResponse(code = 404, message = "Not found"), service should return also 404 IMO
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response searchHsCodeText(@QueryParam("q") String filter, @QueryParam("limit") @DefaultValue("15") int limit,
                                     @QueryParam("language") @DefaultValue("en") String language) {
        if (filter == null || filter.isEmpty()) {
            List<DtoHsCodeText> result = commodityBean.getAllHsCodeTexts();
            return Response.ok(result).build();
        }
        List<DtoHsCodeText> result = commodityBean.searchHsCodeText(filter, language, limit);
        if (result != null) {
            return Response.ok(result).build();
        }
        return Response.serverError().build();
    }
}