package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.DangerousGoodsBean;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoods;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsLite;
import com.dsv.road.shared.masterdata.dto.TransportDivision;
import com.dsv.shared.logger.LoggerInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.DANGEROUS_GOODS;

@RequestScoped
@Path(DANGEROUS_GOODS)
@Interceptors(LoggerInterceptor.class)
@Api(value = DANGEROUS_GOODS)
public class DangerousGoodsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DangerousGoodsResource.class);

    @EJB
    private DangerousGoodsBean dangerousGoodsBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Get a dangerous good",
            notes = "Gets dangerous good by id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoods.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getDangerousGood(@PathParam("id") long id) {
        DtoDangerousGoods dtoDangerousGoods = dangerousGoodsBean.getDangerousGoodsById(id);
        if (dtoDangerousGoods == null) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(dtoDangerousGoods).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Search for dangerous goods",
            notes = "Searches for dangerous goods using given search criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoods.class),
            @ApiResponse(code = 204, message = "No one record has been found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response search(@QueryParam("q") @DefaultValue("") String filter, @QueryParam("limit") @DefaultValue("15") int limit,
                           @QueryParam("division") List<String> division) {
        TransportDivision[] transportDivisionsArray;
        try {
            transportDivisionsArray = TransportDivision.toTransportDivisionsArray(division);
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Error while parsing transport division(s): [" + division + "]", e);
            return Response.serverError().entity(e.getLocalizedMessage()).build();
        }

        List<DtoDangerousGoodsLite> list = dangerousGoodsBean.searchDangerousGoodsLite(filter, transportDivisionsArray, limit);
        if (list == null) {
            return Response.notModified("The search returned null").build();
        }
        return Response.ok(list).build();
    }
}
