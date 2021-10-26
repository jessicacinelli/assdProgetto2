package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
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
		File fos = null;
		fos = new File("Veicolo" + id + ".txt");
		if(fos.exists())
			fos.delete();
		PrintStream ps = null;
		try {
			ps = new PrintStream(fos);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//Client client=ClientBuilder.newClient();
		//WebTarget endpoint=client.target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");

		//TrafficService ts = new TrafficService(endpoint);

		Coordinate c= new Coordinate();
		Intersection s = ts.getNearest(sorgente.getLatitude(), sorgente.getLongitude());
		System.out.println(s.toString());
		Intersection d = ts.getNearest(destinazione.getLatitude(), destinazione.getLongitude());
		System.out.println(d.toString());
		List<Intersection> intersections = new ArrayList<Intersection> (ts.getShortestPath(s.getOsmid(), d.getOsmid(), "Intersection"));
		System.out.println(intersections.toString());
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
			//System.out.println(c.toString());
			JSONObject msg= sample.createJSONObject(id, c);
			
			ps.println(msg);
			
			
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
		
		try {
			sample.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/*	public synchronized void execution(Coordinate c, TrafficService ts) {
		
	}*/

	public VeicoloThread(String id, Sample sample) {
		//	this.sample=sample;
		this.id=id;
		//String clientId = "client" + id;
		topic=topic.replace("+", id);
	}
	
	
	public String getIdVeicolo() {
		return id;
	}
		
	public void setId(String id) {
	
		this.id = id;
	}

	public Coordinate getSorgente() {
		return sorgente;
	}
	public void setSorgente(Coordinate sorgente) {
		this.sorgente = sorgente;
	}
	public Coordinate getDestinazione() {
		return destinazione;
	}
	public void setDestinazione(Coordinate destinazione) {
		this.destinazione = destinazione;
	}
	public Intersection getPercorso() {
		return percorso;
	}
	public void setPercorso(Intersection percorso) {
		this.percorso = percorso;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public TrafficService getTs() {
		return ts;
	}
	public void setTs(TrafficService ts) {
		this.ts = ts;
	}
	public Sample getSample() {
		return sample;
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	public int getQos() {
		return qos;
	}
	public void setQos(int qos) {
		this.qos = qos;
	}






}