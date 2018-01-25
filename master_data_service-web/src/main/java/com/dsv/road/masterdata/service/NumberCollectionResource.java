package com.dsv.road.masterdata.service;

import com.dsv.road.masterdata.ejb.NumberCollectionBean;
import com.dsv.road.masterdata.model.NumberRange;
import com.dsv.road.shared.masterdata.dto.CheckDigitMethodType;
import com.dsv.road.shared.masterdata.dto.NumberCollectionDto;
import com.dsv.road.shared.masterdata.dto.NumberRangeDto;
import com.dsv.road.shared.masterdata.dto.constraints.MasterDataConstants;
import com.dsv.shared.logger.LoggerInterceptor;
import com.dsv.shared.rest.exception.helper.ValidationHelper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.dsv.road.shared.masterdata.rest.MasterDataRestEndpointPath.*;

@RequestScoped
@Path(NUMBER_COLLECTION)
@Interceptors(LoggerInterceptor.class)
@Api(value = NUMBER_COLLECTION)
public class NumberCollectionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberCollectionResource.class);

    @EJB
    private NumberCollectionBean numberCollectionBean;

    @Inject
    private ValidationHelper validationHelper;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create NumberCollection",
            notes = "Creates given NumberCollection"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 304, message = "Insertion returned null"),
            @ApiResponse(code = 400, message = "country, name and collection conflicted with and other collection"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response create(
            @ApiParam(name = "numberCollectionDto", value = "NumberCollection to be created", required = true) NumberCollectionDto numberCollectionDto) {
        LOGGER.info("NumberCollectionResource enter create");
        validationHelper.validateBean(numberCollectionDto).throwOnErrors();
        numberCollectionDto = numberCollectionBean.create(numberCollectionDto);
        if (numberCollectionDto == null) {
            return Response.notModified("The insertion returned null").build();
        }

        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(numberCollectionDto.getId());
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Read NumberCollection",
            notes = "Return NumberCollection with given Id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success", response = NumberCollectionDto.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response read(@PathParam("id") Long id) {
        NumberCollectionDto numberCollectionDto = numberCollectionBean.read(id);
        if (numberCollectionDto == null) {
            LOGGER.debug("Could not find any collections");
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(numberCollectionDto).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Update NumberCollection",
            notes = "Updates NumberCollection with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 304, message = "Insertion returned null"),
            @ApiResponse(code = 400, message = "country, name and collection conflicted with and other collection"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response update(@PathParam("id") Long id,
                           @ApiParam(name = "numberCollectionDto", value = "NumberCollection to be created", required = true) NumberCollectionDto numberCollectionDto) {
        numberCollectionDto = numberCollectionBean.update(id, numberCollectionDto);
        if (numberCollectionDto == null) {
            return Response.notModified("The insertion returned null").build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @ApiOperation(
            value = "Delete NumberCollection",
            notes = "Removes NumberCollection with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response delete(@PathParam("id") long id) {
        numberCollectionBean.delete(id);
        return Response.noContent().build();
    }

    @HEAD
    @Path("{id}")
    @ApiOperation(
            value = "NumberCollection existence",
            notes = "Checks if NumberCollection with given id is present"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "NumberCollection exists"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response exists(@PathParam("id") Long id) {
        if (numberCollectionBean.exists(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Search NumberCollection",
            notes = "Searches for NumberCollection",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumberCollectionDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response search(@DefaultValue("0") @QueryParam("first") int first,
                           @DefaultValue("100") @QueryParam("pagesize") int pageSize,
                           @DefaultValue("") @QueryParam("q") String filter,
                           @DefaultValue("FULL") @QueryParam("format") MasterDataConstants.DetailLevel format
    ) {
        List<NumberCollectionDto> numberCollectionDtos = numberCollectionBean.search(first, pageSize, filter, format);
        return Response.ok(numberCollectionDtos).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/" + NUMBER_RANGES)
    @ApiOperation(
            value = "Add To Collection",
            notes = "Adds NumberRange to Collection"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created", response = URI.class),
            @ApiResponse(code = 304, message = "Insertion returned null"),
            @ApiResponse(code = 500, message = "Internal server error")
    })

    public Response addToRange(@PathParam("id") Long id,
                               @ApiParam(name = "numberRangeDto", value = "Range to be added", required = true) NumberRangeDto numberRangeDto) {
        numberRangeDto = numberCollectionBean.addToRange(id, numberRangeDto);
        if (numberRangeDto == null) {
            return Response.notModified("The insertion returned null").build();
        }
        UriBuilder requestUriBuilder = uriInfo.getRequestUriBuilder();
        URI uri = requestUriBuilder.path("{id}").build(numberRangeDto.getId());
        return Response.created(uri).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{collectionId}/" + NUMBER_RANGES + "/{id}")
    @ApiOperation(
            value = "Read Range",
            notes = "Reads Range with given id in collection with collectionId"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumberRange.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response readRange(@PathParam("collectionId") Long collectionId, @PathParam("id") Long id) {
        NumberRangeDto numberRangeDto = numberCollectionBean.readRange(id);
        if (numberRangeDto == null) {
            LOGGER.debug("Could not find any ranges");
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(numberRangeDto).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{collectionId}/" + NUMBER_RANGES + "/{id}")
    @ApiOperation(
            value = "Update Range",
            notes = "Updates Range with given id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumberRangeDto.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response updateRange(@PathParam("collectionId") Long collectionId, @PathParam("id") Long id,
                                @ApiParam(name = "numberRangeDto", value = "Range to be updated", required = true) NumberRangeDto numberRangeDto) {
        numberRangeDto = numberCollectionBean.updateRange(id, numberRangeDto);
        if (numberRangeDto == null) {
            LOGGER.debug("Could not find any ranges");
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(numberRangeDto).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{collectionId}/" + NUMBER_RANGES + "/{id}")
    @ApiOperation(
            value = "Delete Range",
            notes = "Removes Range with given id, from collection with collectionId"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response deleteRange(@PathParam("collectionId") Long collectionId, @PathParam("id") long id) {
        numberCollectionBean.deleteRange(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/" + NUMBER_RANGES)
    @ApiOperation(
            value = "Range for Collection",
            notes = "Returns ranges for collection with given id",
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumberRangeDto.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getRangesForCollection(@PathParam("id") Long id) {
        List<NumberRangeDto> numberRangeDtos = numberCollectionBean.getRangesForCollection(id);
        return Response.ok(numberRangeDtos).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(NUMBERS+"/{name}")
    @ApiOperation(
            value = "Read next numbers in given collection",
            notes = "return the next numbers in the given collection for the given parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class, responseContainer="List"),
            @ApiResponse(code = 400, message = "Parameters did not math any."),
            @ApiResponse(code = 404, message = "Not found, for given query params"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getNumberList(@PathParam("name") String name,
                                  @QueryParam("amount") @DefaultValue("1") long amount,
                                  @QueryParam("country") @DefaultValue("default") String country,
                                  @QueryParam("location") @DefaultValue("default") String location) {
        List<String> numbers = numberCollectionBean.getNumberList(name, amount, country, location);
        if (numbers == null || numbers.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(numbers).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path(CHECK_DIGITS)
    @ApiOperation(
            value = "Reads possible types of methods for generating the check digits",
            notes = "return the possible types of methods for generating the check digits"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response =  CheckDigitMethodType.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response getCheckDigitMethodTypes() {
        List<CheckDigitMethodType> numbers = Arrays.asList(CheckDigitMethodType.values());
        return Response.ok(numbers).build();
    }
}