package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.idgenerator.IdTypeBean;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.IDS;

@RequestScoped
@Path(IDS)
@Interceptors(LoggerInterceptor.class)
@Api(value = IDS)
public class IdGeneratorResource {

    @EJB
    private IdTypeBean idTypeBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Current Id",
            notes = "Returns current Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Not acceptable. Prefix parameter is mandatory"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @Path("{idType}")
    public Response getCurrentId(@PathParam("idType") String idType) {
        if (idType == null) {
            return Response.status(Status.NOT_ACCEPTABLE)
                    .entity("prefix parameter is mandatory")
                    .build();
        }
        String id = idTypeBean.getId(idType);
        return Response.ok(id).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Next Id",
            notes = "Returns next Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @Path("{idType}")
    public Response getNextId(@PathParam("idType") String idType) {
        String id = idTypeBean.nextId(idType);
        return Response.ok(id).build();
    }

}