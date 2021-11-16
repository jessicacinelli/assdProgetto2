package entity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONObject;

import gps.Coordinate;
import rest.TrafficService;

public class SensorNode extends Thread {


	private String id;
	private Coordinate source;
	private Coordinate destination;
	private Intersection path;
	private String topic = "node/+/coordinates"; 
	private TrafficService ts;
	private MQTTPublisher mqttPublisher;
	private static final long PERIOD= 200;

	int qos=2;


	public SensorNode (String id, Coordinate source, Coordinate destination, MQTTPublisher mqttPublisher ,TrafficService ts) throws MqttException  {
		this.id=id;
		this.source=source;
		this.destination=destination;
		this.mqttPublisher=mqttPublisher;
		topic=topic.replace("+", id);
		this.ts= ts;
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
		
		File fosV = null;
		fosV = new File("VeicoloCoordinate" + id + ".txt");
		if(fosV.exists())
			fosV.delete();
		PrintStream psV = null;
		try {
			psV = new PrintStream(fosV);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File fosP = null;
		fosP = new File("VeicoloPath" + id + ".txt");
		if(fosP.exists())
			fosP.delete();
		PrintStream psP = null;
		try {
			psP = new PrintStream(fosP);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xV = "XV = [";
		String yV = "YV = [";
		String xP = "XP = [";
		String yP = "YP = [";
		
		Coordinate c= new Coordinate();
		Intersection s = ts.getNearest(source.getLatitude(), source.getLongitude());
		
		try {
			mqttPublisher.publish(topic + "/source", qos, (s.getOsmid()+"").getBytes());
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Intersection d = ts.getNearest(destination.getLatitude(), destination.getLongitude());
		
		try {
			mqttPublisher.publish(topic + "/destination", qos, (d.getOsmid()+"").getBytes());
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Intersection> intersections = new ArrayList<Intersection> (ts.getShortestPath(s.getOsmid(), d.getOsmid(), "Intersection"));
		//System.out.println(intersections.toString());
		int getStart =0;
		int getDest=1;
		//int start = intersections.get(getStart).getOsmid();
		//int dest = intersections.get(getDest).getOsmid();
		Intersection Intstart = intersections.get(getStart);
		
		
		Intersection Intdest = intersections.get(getDest);
		xP=xP.concat(" " + Intstart.getCoordinate().getLatitude());
		yP=yP.concat( " " + Intstart.getCoordinate().getLongitude());
		Street st = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
		//System.out.println("LUNGHEZZA: " + street.getLenght());
		double startTime = System.currentTimeMillis();
		
		double dist;

		double count=1;
		while(true) {
			//  System.out.println(PERIOD/scaleFactor);
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
			}

			double endTime = System.currentTimeMillis();
			double seconds = (endTime - startTime) / 1000;
			//System.out.println("SECONDI " +seconds);
			dist = st.Distance(st.getFfs(),seconds);

			//System.out.println("DISTANZA " + dist);
			c= (newPoint(st.getLenght(), dist, Intstart.getCoordinate(), Intdest.getCoordinate()));
			xV=xV.concat(" " + c.getLatitude());
			yV=yV.concat( " " + c.getLongitude());
			count++;
			//System.out.println(c.toString());
			JSONObject msg= createJSONObject(id, c);
			
			ps.println(msg);
			
			
			try {
				mqttPublisher.publish(topic, qos, msg.toJSONString().getBytes());
			} catch (MqttException e) {
				
			}
			//street.getLenght()-dist)<=dist*seconds ||
			if( (st.getLenght()-dist)<=st.getLenght()/count ||st.getLenght()<=dist) { //street.getLenght()<=dis per distanze molto piccole
				//System.out.println("count");
				//System.out.println(street.getLenght()-dist);
				//System.out.println(dist/seconds);
				if(Intdest.getOsmid()==d.getOsmid() || getDest==intersections.size() )
					break;
				else {
				Intstart = intersections.get(++getStart);
				
				//  System.out.println("START: " + Intstart.toString());
				Intdest = intersections.get(++getDest);
				xP=xP .concat(" " + Intstart.getCoordinate().getLatitude());
				yP=yP.concat(" " + Intstart.getCoordinate().getLongitude());
				//  System.out.println("DEST: " + Intdest.toString());
				st = ts.getStreet(Intstart.getOsmid(), Intdest.getOsmid());
				//  System.out.println("STREET: " + street.toString());
				//System.out.println("LUNGHEZZA: " + street.getLenght());
				//  System.out.println(street.getFfs());
				startTime = System.currentTimeMillis();
				count=1;
				}
			}
		}
		xV= xV.concat(" ]" );
		yV= yV.concat(" ]" );
		xP= xP.concat(" ]" );
		yP= yP.concat(" ]" );
		psV.println(xV);
		psV.println(yV);
		psP.println(xP);
		psP.println(yP);
		ps.close();
		psV.close();
		psP.close();
		try {
			mqttPublisher.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	public Coordinate newPoint(double distance, double metripercorsi, Coordinate a, Coordinate b)
	{
		double lat= (distance-metripercorsi)/distance * (b.getLatitude()-a.getLatitude());
		double lon=metripercorsi/distance * (b.getLongitude()- a.getLongitude());
		Coordinate point = new  Coordinate(b.getLatitude()- lat, a.getLongitude() + lon);
		
		return point;
	}
	public JSONObject createJSONObject(String id, Coordinate c) {
		JSONObject obj=new JSONObject();
		obj.put("id",id ); 
		obj.put("latitude",c.getLatitude());    
		obj.put("longitude",c.getLongitude());    
		return obj;
	}


	public String getIdVeicolo() {
		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public Coordinate getsource() {
		return source;
	}
	public void setsource(Coordinate source) {
		this.source = source;
	}
	public Coordinate getdestination() {
		return destination;
	}
	public void setdestination(Coordinate destination) {
		this.destination = destination;
	}
	public Intersection getpath() {
		return path;
	}
	public void setpath(Intersection path) {
		this.path = path;
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
	public MQTTPublisher getmqttPublisher() {
		return mqttPublisher;
	}
	public void setmqttPublisher(MQTTPublisher mqttPublisher) {
		this.mqttPublisher = mqttPublisher;
	}
	public int getQos() {
		return qos;
	}
	public void setQos(int qos) {
		this.qos = qos;
	}






}