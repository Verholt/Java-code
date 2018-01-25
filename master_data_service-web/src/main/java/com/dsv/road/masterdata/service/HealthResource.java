package com.dsv.road.masterdata.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.dsv.shared.dto.DtoHealthStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.HEALTH_PATH;


@Path(HEALTH_PATH)
@RequestScoped
@Api(value = HEALTH_PATH)
public class HealthResource {



    //WORKAROUND
    //This overriding method is necessary because PARENT-LAST functionality has been disabled for this module.
    //If you remove this 'redundant' method, the HealthEndpoint.health() will execute in common-technical-endpoints module context that doesn't have project.properties resource
    @ApiOperation(
            value = "Health",
            notes = "Returns healthStatus"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GET
    @Produces({"application/json"})
    public Response health(){
        ResourceBundle bundle = ResourceBundle.getBundle("project", Locale.ENGLISH);
        String applicationVersion = bundle.getString("project.version");
        DtoHealthStatus status = new DtoHealthStatus();
        status.setVersion(applicationVersion);
        return Response.ok(status).build();
    }

}
