package application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;

import gps.Coordinate;
import strada.Intersection;
import strada.Street;

public class VeicoloThread extends Thread {


	private String id;
	private Coordinate sorgente;
	private Coordinate destinazione;
	private Intersection percorso;
	private String topic = "node/+/coordinates"; 
	private TrafficService ts;
	private Sample sample;


	int qos=2;

	/**public VeicoloThread() {

  }*/

	public VeicoloThread (String id, Coordinate sorgente, Coordinate destinazione, Sample sample) {
		this.id=id;
		this.sorgente=sorgente;
		this.destinazione=destinazione;
		this.sample=sample;
		topic=topic.replace("+", id);

	}
	public VeicoloThread (String id, Coordinate sorgente, Coordinate destinazione, Sample sample ,TrafficService ts) throws MqttException  {
		this.id=id;
		this.sorgente=sorgente;
		this.destinazione=destinazione;
		this.sample=sample;
		topic=topic.replace("+", id);
		this.ts= ts;
		/*Client client=ClientBuilder.newClient();
		WebTarget endpoint=client.target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");

	 ts = new TrafficService(endpoint);*/
	}

	public  void run() {

		//Client client=ClientBuilder.newClient();
		//WebTarget endpoint=client.target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");

		//TrafficService ts = new TrafficService(endpoint);

		Coordinate c= new Coordinate();
		Intersection s = ts.getNearest(sorgente.getLatitude(), sorgente.getLongitude());

		Intersection d = ts.getNearest(destinazione.getLatitude(), destinazione.getLongitude());

		List<Intersection> intersections = new ArrayList<Intersection> (ts.getShortestPath(s.getOsmid(), d.getOsmid(), "Intersection"));

		int getStart =0;
		int getDest=1;
		//int start = intersections.get(getStart).getOsmid();
		//int dest = intersections.get(getDest).getOsmid();
		Intersection Intstart = intersections.get(getStart);

		Intersection Intdest = intersections.get(getDest);

		Street st = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
		//System.out.println("LUNGHEZZA: " + street.getLenght());
		double startTime = System.currentTimeMillis();
		int scaleFactor=6;
		int period=1000;
		double dist;

		double controllo=1;
		while(true) {
			//  System.out.println(period/scaleFactor);
			try {
				Thread.sleep(period/scaleFactor);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double endTime = System.currentTimeMillis();
			double seconds = (endTime - startTime) / period;
			//System.out.println("SECONDI " +seconds);
			dist = st.Distance(st.getFfs(),seconds);

			//System.out.println("DISTANZA " + dist);
			c= (st.newPoint(st.getLenght(), dist, Intstart.getCoordinate(), Intdest.getCoordinate()));
			controllo++;
			System.out.println(c.toString());
			JSONObject msg= sample.createJSONObject(id, c);

			try {
				sample.publish(topic, qos, msg.toJSONString().getBytes());
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//street.getLenght()-dist)<=dist*seconds ||
			if( (st.getLenght()-dist)<=st.getLenght()/controllo ||st.getLenght()<=dist) { //street.getLenght()<=dis per distanze molto piccole
				//System.out.println("CONTROLLO");
				//System.out.println(street.getLenght()-dist);
				//System.out.println(dist/seconds);
				if(Intdest.getOsmid()==d.getOsmid())
					break;

				Intstart = intersections.get(++getStart);
				//  System.out.println("START: " + Intstart.toString());
				Intdest = intersections.get(++getDest);
				//  System.out.println("DEST: " + Intdest.toString());
				st = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
				//  System.out.println("STREET: " + street.toString());
				//System.out.println("LUNGHEZZA: " + street.getLenght());
				//  System.out.println(street.getFfs());
				startTime = System.currentTimeMillis();
				controllo=1;
			}
		}
	}
	
	public synchronized void execution(Coordinate c, TrafficService ts) {
		
	}

	public VeicoloThread(String id, Sample sample) {
		//	this.sample=sample;
		this.id=id;
		//String clientId = "client" + id;
		topic=topic.replace("+", id);
	}






}