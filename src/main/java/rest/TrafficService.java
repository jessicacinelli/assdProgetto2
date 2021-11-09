package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import entity.Intersection;
import entity.Street;
import gps.Coordinate;

public class TrafficService {
	private WebTarget endpoint=ClientBuilder.newClient().target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");
	private WebTarget resource;

	public TrafficService() {
	}


	public  TrafficService(WebTarget endpoint) {
		this.endpoint = endpoint;
	}

	//GET /intersections/nearest
	public  Intersection getNearest(double latitude, double longitude) {

		WebTarget resource= endpoint.path("intersections/nearest").queryParam("latitude", latitude).queryParam("longitude", longitude);	
		Intersection i = resource.request().accept("application/json").get(Intersection.class);
	
		return i;				
	}

	//GET intersections/{osmid}
	public  Intersection getIntersection(int osmid) {
		WebTarget resource=endpoint.path("intersections/"+ osmid);
		Intersection i = resource.request().accept("application/json").get(Intersection.class);
		
		return i;
	}

	//GET /streets/{linkId}
	public  Street getStreet(String linkId) {
		resource=endpoint.path("streets/"+linkId);
	Street s = resource.request().accept("application/json").get(Street.class);

	return s;
	}
	
	//GET ​/streets
	public  Street getStreet(int osmidStart, int osmidDest) {
		resource=endpoint.path("streets").queryParam("osmidStart", osmidStart).queryParam("osmidDest", osmidDest);
	String st = resource.request().accept("application/json").get(String.class);
	Street street = Street.parseStreetJson(st);
	
	return street;
	
	}
	
	//GET /shortestPaths
	public synchronized List getShortestPath(int source, int destination, String type) {
		WebTarget resource=endpoint.path("shortestPaths").queryParam("source", source).queryParam("destination", destination).queryParam("type", type);
		//   List<Coordinate> coo= client.target("http://assd-traffic-service-progetto2.router.default.svc.cluster.local/assdTrafficService/rest/").path("shortestPaths").queryParam("source", 40233).queryParam("destination", 40236).queryParam("type", "Coordinate").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Coordinate>>() {});
		
		if(type.equals("Coordinate")) {
			List<Coordinate> list= resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Coordinate>>() {});
			return list;
		}
		else if(type.equals("Intersection")) {
			List<Intersection> list= resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Intersection>>() {});
			return list;
		}
		
		return null;
	}
	
}





