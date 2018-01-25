package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.DangerousGoodsBean;
import com.dsv.road.masterdata.exception.DangerousGoodsException;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoods;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsBundle;
import com.dsv.road.shared.masterdata.dto.DtoDangerousGoodsText;
import com.dsv.shared.collections.CollectionUtils;
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

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.DANGEROUS_GOODS_BUNDLES;

@RequestScoped
@Path(DANGEROUS_GOODS_BUNDLES)
@Interceptors(LoggerInterceptor.class)
@Api(value = DANGEROUS_GOODS_BUNDLES)
public class DangerousGoodsBundleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DangerousGoodsBundleResource.class);

    @EJB
    private DangerousGoodsBean dangerousGoodsBean;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Read dangerousGoodsBundle",
            notes = "Returns dangerous goods bundle by given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoodsBundle.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readDangerousGoodsBundle(@PathParam("id") Long id) {
        DtoDangerousGoodsBundle dtoDangerousGoodsBundle = dangerousGoodsBean.getDangerousGoodsBundle(id);
        if (dtoDangerousGoodsBundle != null) {
            return Response.ok(dtoDangerousGoodsBundle).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read dangerousGoodsBundle",
            notes = "Returns dangerous goods bundle by given Time"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoDangerousGoodsBundle.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readDangerousGoodsBundleByTime(@QueryParam("time") @DefaultValue("") String time) throws DangerousGoodsException {
        DtoDangerousGoodsBundle dtoDangerousGoodsBundle = dangerousGoodsBean.getDangerousGoodsBundleByTime(time);
        if (dtoDangerousGoodsBundle != null) {
            return Response.ok(dtoDangerousGoodsBundle).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create DangerousGoodsBundle",
            notes = "Creates dangerous goods bundle"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "Not created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response createDangerousGoodsBundle(
            @ApiParam(name = "dtoDangerousGoodsBundle", value = "DangerousGoodsBundle to be created", required = true) DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        try {
            fillCreatedByUser(dtoDangerousGoodsBundle);
            DtoDangerousGoodsBundle createdDtoDangerousGoodsBundle = dangerousGoodsBean.insertDangerousGoodsBundle(dtoDangerousGoodsBundle);
            if (createdDtoDangerousGoodsBundle == null) {
                return Response.notModified("The insertion returned null").build();
            }
            UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
            URI uri = requestUriBuilder.path("{id}").build(createdDtoDangerousGoodsBundle.getId());
            return Response.created(uri).build();
        } catch (DangerousGoodsException e) {
            LOGGER.error("Error while creating dangerous goods bundle", e);
            return Response.serverError().entity(e.getLocalizedMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update DangerousGoodsBundle",
            notes = "Updates dangerous goods bundle"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateDangerousGoodsBundle(@PathParam("id") long id,
            @ApiParam(name = "dtoDangerousGoodsBundle", value = "DangerousGoodsBundle to be updated", required = true) DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        dtoDangerousGoodsBundle.setUpdatedBy(WASSecurityUtil.getAuthenticatedUserID());
        dtoDangerousGoodsBundle.setId(id);
        dangerousGoodsBean.updateDangerousGoodsBundle(dtoDangerousGoodsBundle);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Delete DangerousGoodsBundle",
            notes = "Removes dangerous goods bundle with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteDangerousGoodsBundle(@PathParam("id") long id) {
        dangerousGoodsBean.deleteDangerousGoodsBundle(id);
        return Response.noContent().build();
    }

    @HEAD
    @Path("{id}")
    @ApiOperation(
            value = "DangerousGoodsBundle existence",
            notes = "Checks if DangerousGoodsBundle with given id exists "
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("id") Long id) {
        if (dangerousGoodsBean.exists(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void fillCreatedByUser(DtoDangerousGoods dtoDangerousGoods) {
        String authUser = WASSecurityUtil.getAuthenticatedUserID();
        dtoDangerousGoods.setCreatedBy(authUser);
        if (!CollectionUtils.isEmpty(dtoDangerousGoods.getDangerousGoodsTexts())) {
            for (DtoDangerousGoodsText dtoDangerousGoodsText : dtoDangerousGoods.getDangerousGoodsTexts()) {
                dtoDangerousGoodsText.setCreatedBy(authUser);
            }
        }
    }

    private void fillCreatedByUser(DtoDangerousGoodsBundle dtoDangerousGoodsBundle) {
        String authUser = WASSecurityUtil.getAuthenticatedUserID();
        dtoDangerousGoodsBundle.setCreatedBy(authUser);
        if (!CollectionUtils.isEmpty(dtoDangerousGoodsBundle.getDangerousGoodsList())) {
            for (DtoDangerousGoods dtoDangerousGoods : dtoDangerousGoodsBundle.getDangerousGoodsList()) {
                fillCreatedByUser(dtoDangerousGoods);
            }
        }
    }
}
