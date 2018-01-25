package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.CurrencyBean;
import com.dsv.road.shared.masterdata.dto.DtoCurrency;
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

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.CURRENCIES;

@RequestScoped
@Path(CURRENCIES)
@Interceptors(LoggerInterceptor.class)
@Api(value = CURRENCIES)
public class CurrencyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyResource.class);

    @EJB
    private CurrencyBean currencyBean;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read Currencies",
            notes = "Returns list of currency",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted", response = DtoCurrency.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readAll() {
        List<DtoCurrency> dtoCurrencyList = currencyBean.getCurrencies();

        if (dtoCurrencyList == null) {
            LOGGER.debug("Could not find any currencies.");
            return errorResponse("CurrencyList is empty");
        } else {
            return Response.ok(dtoCurrencyList).build();
        }
    }

    @GET
    @Path("{shortAbbreviation}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Read Currency",
            notes = "Returns Currency by given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DtoCurrency.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("shortAbbreviation") String shortAbbreviation) {
        LOGGER.debug("Fetching Currency for shortAbbreviation: " + shortAbbreviation);

        DtoCurrency dtoCurrency = currencyBean.getCurrency(shortAbbreviation);
        if (dtoCurrency != null) {
            return Response.ok(dtoCurrency).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create Currency",
            notes = "Returns Currency by given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name = "dtoCurrency", value = "Currency to be created", required = true) DtoCurrency dtoCurrency) {
        dtoCurrency.setCreatedBy(WASSecurityUtil.getAuthenticatedUserID());
        DtoCurrency createdCurrency = currencyBean.createCurrency(dtoCurrency);
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{shortAbbreviation}").build(createdCurrency.getShortAbbreviation());
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Delete Currency",
            notes = "Removes Currency with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("shortAbbreviation") String shortAbbreviation) {
        boolean success = currencyBean.deleteCurrency(shortAbbreviation);
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
            value = "Update Currency",
            notes = "Updates Currency with given shortAbbreviation"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = DtoCurrency.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("shortAbbreviation") String shortAbbreviation, @ApiParam(name = "dtoCurrency", value = "Currency to be updated", required = true) DtoCurrency dtoCurrency) {
        dtoCurrency.setUpdatedBy(WASSecurityUtil.getAuthenticatedUserID());
        dtoCurrency.setShortAbbreviation(shortAbbreviation);
        DtoCurrency updatedCurrency = currencyBean.updateCurrency(dtoCurrency);
        return Response.ok(updatedCurrency).build();
    }

    @HEAD
    @Path("{shortAbbreviation}")
    @ApiOperation(
            value = "Currency Existence",
            notes = "Checks if currency with given abbreviation is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("shortAbbreviation") String shortAbbreviation) {
        if (currencyBean.exists(shortAbbreviation)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private Response errorResponse(String error) {
        return RestApplication.errorResponse(error);
    }
}
