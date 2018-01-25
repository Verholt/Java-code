package com.dsv.road.masterdata.service;

import com.dsv.road.shared.masterdata.dto.DtoLogMessage;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.LOG;

/**
 * Created by EXT.piotr.komisarki on 2016-02-22.
 */
@RequestScoped
@Path(LOG)
@Interceptors(LoggerInterceptor.class)
@Api(value = LOG)
public class LogResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Log message to central system"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public Response logToCentralSystem(DtoLogMessage message) {
        LOGGER.error("Received log: " + message.getDate() + " - " + message.getMessage());
        return Response.ok().build();
    }
}