package entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import gps.Coordinate;


@XmlRootElement

public class Street implements Serializable{
	private double linkId; 
	private int from;
	private int to;
	private double lenght; //link lenght [espressa in metri]

	private int speedlimit;
	private String name;
	private double weight; //average travel time [s]  //tempo finale - tempo iniziale 
	
	private double ffs; // free flow speed [Km/h] (Velocit� media, dipendente dal traffico)

	private List<Coordinate> coordinates;

	 @JsonCreator
	    public Street(
	            @JsonProperty("linkId") final double linkId,
	            @JsonProperty("from") final int from,
	            @JsonProperty("to") final int to,
	            @JsonProperty("lenght") final double lenght,
	            @JsonProperty("speedlimit") final int speedlimit,
	            @JsonProperty("name") final String name,
	            @JsonProperty("weight") final double weight,
	            @JsonProperty("fft") final double fft,
	            @JsonProperty("coordinates") final List<Coordinate> coordinates
	    ) {
		 this.linkId = linkId;
			this.from = from;
			this.to = to;
			this.lenght =lenght;
			this.speedlimit = speedlimit;
			this.name = name;
			this.weight = weight;
			this.ffs = ffs;
			this.coordinates=coordinates;
	    }

	    public static Street parseStreetJson(String json) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            return objectMapper.readValue(json, Street.class);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	
	public Street() {}
	
	public Street(int linkid, int fromPoint, int toPoint, double lenght, int speedlimit, String name, double weight, double ffs) 
	{
		this.linkId = linkid;
		this.from = fromPoint;
		this.to = toPoint;
		this.lenght = lenght;
		this.speedlimit = speedlimit;
		this.name = name;
		this.weight = weight;
		this.ffs = ffs;
		
	}

	public Street(int linkid, int fromPoint, int toPoint, double lenght, int speedlimit, String name, double weight, double ffs, List<Coordinate> coordinates) 
	{
		this.linkId = linkid;
		this.from = fromPoint;
		this.to = toPoint;
		this.lenght = lenght;
		this.speedlimit = speedlimit;
		this.name = name;
		this.weight = weight;
		this.ffs = ffs;
		this.coordinates = coordinates;
	}

	public double getLinkid() {
		return linkId;
	}

	public void setLinkid(int linkid) {
		this.linkId = linkid;
	}

	public int getFromPoint() {
		return from;
	}

	public void setFromPoint(int fromPoint) {
		this.from = fromPoint;
	}

	public int getToPoint() {
		return to;
	}

	public void setToPoint(int toPoint) {
		this.to = toPoint;
	}

	public double getLenght() {
		return lenght;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}

	public int getSpeedlimit() {
		return speedlimit;
	}

	public void setSpeedlimit(int speedlimit) {
		this.speedlimit = speedlimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getFfs() {
		return ffs;
	}

	public void setFfs(double ffs) {
		this.ffs = ffs;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}
	
	public double Distance(double ffs, double w )
	{
		double dist = (ffs / 3.6)* (double)w; //calcola la distanza percorsa in metri
		//System.out.println(dist);
		return dist;
	}
	
	
	@Override
	public String toString() {
		return "Street [linkid=" + linkId + ", fromPoint=" + from + ", toPoint=" + to + ", lenght=" + lenght
				+ ", speedlimit=" + speedlimit + ", name=" + name + ", weight=" + weight + ", ffs=" + ffs + ", c=" + coordinates
				+ "]";
	}

	public Coordinate newPoint(double distance, double metripercorsi, Coordinate a, Coordinate b)
	{
		//System.out.println("DISTANCE:" + distance);
	//	System.out.println("MP:" + metripercorsi);
		//distance -> lunghezza del link
		//metripercorsi -> metri effettivamente percorsi
		double lat= (distance-metripercorsi)/distance * (b.getLatitude()-a.getLatitude());
		double lon=metripercorsi/distance * (b.getLongitude()- a.getLongitude());
		Coordinate point = new  Coordinate(b.getLatitude()- lat, a.getLongitude() + lon);
		
		return point;
	}
}
