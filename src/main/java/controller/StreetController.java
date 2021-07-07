package controller;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import gps.Coordinate;



@Consumes("application/json")
@Produces("application/json")
@Path("/otm")
public class StreetController {

	@GET
	@Path("/intersections​/{osmid}")
	public Response getIntersections(@PathParam("osmid") int osmid) {
		return null;
	}
	
	@GET
	@Path("/intersections​/nearest")
	public Response getNearest(@QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude) {
		return null;
	}
	
	@GET 
	@Path("​/streets​/{linkId}")
	public Response getStreet(@PathParam("linkId") int linkId) {
		return null;
	}
	
	
	@GET
	@Path("/streets")
	public Response getStreets(@QueryParam("osmidStart") int osmidStart,@QueryParam("osmidDest") int osmidDest) {
		return null;
	}
	
	@GET
	@Path("/shortestPaths")
	public Response getShortestPath(@QueryParam("source") Coordinate source, @QueryParam("destination") Coordinate destination, @QueryParam("type") String type  ) {
		return null;
	}
	
	

}
