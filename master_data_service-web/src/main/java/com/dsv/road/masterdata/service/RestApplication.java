package com.dsv.road.masterdata.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@ApplicationPath(RestApplication.REST_APPLICATION_PATH)
public class RestApplication extends Application {

	public static final String REST_APPLICATION_PATH = "rest";

	public static Response errorResponse(Throwable error) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.TEXT_PLAIN).build();
	}

	public static Response errorResponse(String error) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.TEXT_PLAIN).build();
	}
}
