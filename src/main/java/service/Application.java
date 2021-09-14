package application;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;

import strada.Intersection;
import strada.Street;

public class Application {


	public Application() {}
	String topic;
	int qos;
	
	public Application (String topic, int qos) {
		this.topic=topic;
		this.qos=qos;
	
	}
	
	public void run(Veicolo veicolo, TrafficService ts, Sample sample) throws InterruptedException, MqttException {
		Intersection sorgente = ts.getNearest(veicolo.getSorgente().getLatitude(), veicolo.getSorgente().getLongitude());
		Intersection destinazione = ts.getNearest(veicolo.getDestinazione().getLatitude(), veicolo.getDestinazione().getLongitude());
		List<Intersection> intersections = ts.getShortestPath(sorgente.getOsmid(), destinazione.getOsmid(), "Intersection");
		//System.out.println(intersections.size());
		int getStart =0;
		int getDest=1;
		//int start = intersections.get(getStart).getOsmid();
		//int dest = intersections.get(getDest).getOsmid();
		Intersection Intstart = intersections.get(getStart);
		Intersection Intdest = intersections.get(getDest);
		
		topic=topic.replace("+", veicolo.getId());
		
		
		Street s = new Street(); 
		Street street = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
		//System.out.println("LUNGHEZZA: " + street.getLenght());
		double startTime = System.currentTimeMillis();
		int scaleFactor=6;
		int period=1000;
		double dist;
	
		double controllo=1;
		while(true) {
		//	System.out.println(period/scaleFactor);
			Thread.sleep(period/scaleFactor);
			
			double endTime = System.currentTimeMillis();
			double seconds = (endTime - startTime) / period;
			//System.out.println("SECONDI " +seconds);
			dist = s.Distance(street.getFfs(),seconds);
			
			//System.out.println("DISTANZA " + dist);
			veicolo.setCoordinates(s.newPoint(street.getLenght(), dist, Intstart.getCoordinate(), Intdest.getCoordinate()));
			controllo++;
			JSONObject msg= sample.createJSONObject(veicolo);
			
			sample.publish(topic, qos, msg.toJSONString().getBytes());
			//street.getLenght()-dist)<=dist*seconds ||
			if( (street.getLenght()-dist)<=street.getLenght()/controllo ||street.getLenght()<=dist) { //street.getLenght()<=dis per distanze molto piccole
				//System.out.println("CONTROLLO");
				//System.out.println(street.getLenght()-dist);
				//System.out.println(dist/seconds);
				

				if(Intdest.getOsmid()==destinazione.getOsmid())
					break;
		
				Intstart = intersections.get(++getStart);
			//	System.out.println("START: " + Intstart.toString());
				Intdest = intersections.get(++getDest);
			//	System.out.println("DEST: " + Intdest.toString());
				street = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
			//	System.out.println("STREET: " + street.toString());
				//System.out.println("LUNGHEZZA: " + street.getLenght());
			//	System.out.println(street.getFfs());
				startTime = System.currentTimeMillis();
				controllo=1;
			}
			
		}

	}
	
	
	/**
	
	public void run(Veicolo veicolo, TrafficService ts, Samples sample) throws InterruptedException, MqttException {
		Intersection sorgente = ts.getNearest(veicolo.getSorgente().getLatitude(), veicolo.getSorgente().getLongitude());
		Intersection destinazione = ts.getNearest(veicolo.getDestinazione().getLatitude(), veicolo.getDestinazione().getLongitude());
		List<Intersection> intersections = ts.getShortestPath(sorgente.getOsmid(), destinazione.getOsmid(), "Intersection");
		//System.out.println(intersections.size());
		int getStart =0;
		int getDest=1;
		//int start = intersections.get(getStart).getOsmid();
		//int dest = intersections.get(getDest).getOsmid();
		Intersection Intstart = intersections.get(getStart);
		Intersection Intdest = intersections.get(getDest);
		
		topic=topic.replace("+", veicolo.getId());
		
		
		Street s = new Street(); 
		Street street = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
		long startTime = System.currentTimeMillis();
		int scaleFactor=1;
		int period=1000;
		double dist;
	
		while(true) {
			Thread.sleep(period/scaleFactor);
			long endTime = System.currentTimeMillis();
			long seconds = (endTime - startTime) / 1000;
			dist = s.Distance(street.getFfs(),seconds);

			veicolo.setCoordinates(s.newPoint(street.getLenght(), dist, Intstart.getCoordinate(), Intdest.getCoordinate()));
			
			//JSONObject msg= sample.createJSONObject(veicolo);
			
			sample.publishSamples( topic, veicolo);
			if((street.getLenght()-dist)<=dist/seconds) {
				startTime = System.currentTimeMillis();

				if(Intdest.getOsmid()==destinazione.getOsmid())
					break;
		
				Intstart = intersections.get(++getStart);
			//	System.out.println("START: " + Intstart.toString());
				Intdest = intersections.get(++getDest);
			//	System.out.println("DEST: " + Intdest.toString());
				street = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
			//	System.out.println("STREET: " + street.toString());
			}
		}

	}*/
}

