package strada;

import java.util.ArrayList;

import gps.Coordinate;

public class Street 
{
	private int linkid; 
	private int fromPoint;
	private int toPoint;
	private double length; //link lenght [espressa in metri]

	private int speedlimit;
	private String name;
	private double weight; //average travel time [s]  //tempo finale - tempo iniziale 
	
	private double ffs; // free flow speed [Km/h] (Velocità media, dipendente dal traffico)

	private ArrayList<Coordinate> c;

	public Street() {}
	
	public Street(int linkid, int fromPoint, int toPoint, double length, int speedlimit, String name, double weight, double ffs) 
	{
		this.linkid = linkid;
		this.fromPoint = fromPoint;
		this.toPoint = toPoint;
		this.length = length;
		this.speedlimit = speedlimit;
		this.name = name;
		this.weight = weight;
		this.ffs = ffs;
		this.c = new ArrayList<Coordinate>();
	}

	public int getLinkid() {
		return linkid;
	}

	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}

	public int getFromPoint() {
		return fromPoint;
	}

	public void setFromPoint(int fromPoint) {
		this.fromPoint = fromPoint;
	}

	public int getToPoint() {
		return toPoint;
	}

	public void setToPoint(int toPoint) {
		this.toPoint = toPoint;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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

	public ArrayList<Coordinate> getCoordinate() {
		return c;
	}

	public void setCoordinate(ArrayList<Coordinate> c) {
		this.c = c;
	}
	
	public double Distance(long w )
	{
		double dist = (ffs * 3.6)* w; //calcola la distanza percorsa 
		return dist;
	}
	
	public Coordinate newPoint(double distance, double metripercorsi, Coordinate a, Coordinate b)
	{
		//distance -> lunghezza del link
		//metripercorsi -> metri effettivamente percorsi
		double lat= (distance-metripercorsi)/distance * (b.getLatitude()-a.getLatitude());
		double lon=metripercorsi/distance * (b.getLongitude()- a.getLongitude());
		Coordinate point = new Coordinate(lat, lon);
		
		return point;
	}
}
