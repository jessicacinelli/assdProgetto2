package controller;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import gps.Coordinate;
import strada.Intersection;
import strada.Street;



@Consumes("application/json")
@Produces("application/json")
@Path("/otm")
public interface StreetController {

	
	@GET
	@Path("/intersections/{osmid}")
	public Intersection getIntersection(@PathParam("osmid") int osmid);
	
	@GET
	@Path("/intersections/nearest")
	public Intersection getNearest(@QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude);
	
	@GET 
	@Path("/streets/{linkId}")
	public Street getStreet(@PathParam("linkId") int linkId);
	
	@GET
	@Path("/streets")
	public HashMap<Integer , Street> getStreets(@QueryParam("osmidStart") int osmidStart,@QueryParam("osmidDest") int osmidDest);
	
	@GET
	@Path("/shortestPaths")
	public Object getShortestPath(@QueryParam("source") int osmidSource, @QueryParam("destination") int osmidDestination, @QueryParam("type") String type  );
	
	

}
