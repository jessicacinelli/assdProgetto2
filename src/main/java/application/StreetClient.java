package application;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import strada.Intersection;

public class StreetClient {

	public static void main(String[] args) throws ParseException{
		try {

			Client client=ClientBuilder.newClient();
			WebTarget endpoint=client.target("http://localhost:8081/Progetto/assdTrafficService/rest");
			
			//@GET@Path("/intersections/{osmid}")
			//public Intersection getIntersection(@PathParam("osmid") int osmid);
			//GET /hotels
			WebTarget resource= endpoint.path("/intersection/0");
			Intersection i = resource.request().accept("application/json").get(Intersection.class);
			
			System.out.println("Intersection: " + i.toString());
			System.out.println();
	/**		
			//GET /hotels/1
			
			resource=endpoint.path("/1");
			Hotel hotel= resource.request().accept("application/json").get(Hotel.class);
			System.out.println("Info hotel");
			System.out.println(hotel);
			
			//POST /bookings
			SimpleDateFormat d= new SimpleDateFormat("dd-MM-yyyy");
			
			Rate rate= new Rate(1, "double", d.parse("26-06-2021"), d.parse("07-07-2021"), 150.0);
			
			resource = endpoint.path("/bookings").queryParam("creditCard", 1234);
			Response res=resource.request().accept("application/json").post(Entity.entity(rate, MediaType.APPLICATION_JSON));
			
			String uriPath = res.getLocation().getPath();
			
			System.out.println("Created booking: " + rate + '\n' + uriPath);
			System.out.println();
			
			//POST /bookings
			
			Rate rate2= new Rate(0, "double", d.parse("26-06-2021"), d.parse("07-07-2021"), 150.0);
			
			resource = endpoint.path("/bookings").queryParam("creditCard", 1234);
			Response res2=resource.request().accept("application/json").post(Entity.entity(rate, MediaType.APPLICATION_JSON));
			
			String uriPath2 = res.getLocation().getPath();
			
			System.out.println("Created booking: " + rate2 + '\n' + uriPath2);
			System.out.println();
			
			//GET /bookings/1
			resource=endpoint.path("/bookings").queryParam("oid", 1);
			Order order= resource.request().accept("application/json").get(Order.class);
			System.out.println("Order 1: ");
			System.out.println(order);
			System.out.println();
			
			//GET /bookings/1234
			resource=endpoint.path("/bookings/1234");
			Bookings resultsBookings= resource.request().accept("application/json").get(Bookings.class);
			System.out.println(resultsBookings);
			System.out.println();
	*/
			} catch (NotFoundException e) {
				System.out.println("Resource not found " + e);
				e.printStackTrace();
		}

	
		}
}
