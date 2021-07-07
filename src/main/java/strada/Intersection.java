package strada;

import java.util.ArrayList;

import gps.Coordinate;

public class Intersection 
{
	private int osmid; //Open Street Map ID
	private Coordinate coordinate;
	private ArrayList<Street> streets;
	
	
	public Intersection() {}
	
	public Intersection(int osmid, Coordinate coordinate)
	{
		this.osmid = osmid;
		this.coordinate = coordinate;
		this.streets = new ArrayList<Street>();
	}

	public int getOsmid() {
		return osmid;
	}
	public void setOsmid(int osmid) {
		this.osmid = osmid;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public ArrayList<Street> getStreets() {
		return streets;
	}
	public void setStreets(ArrayList<Street> streets) {
		this.streets = streets;
	}
	
	/* Questa funzione calcola la distanza tra due punti 
	in linea retta date le coordinate in
	latitudine e longitudine */
	public double disgeo (Coordinate a, Coordinate b)
	{
	     double x = 0;
	     double y = 0;
	     double dist = 0;
	     
	     x = (b.getLatitude() - a.getLatitude());
	     y = (b.getLongitude() - a.getLongitude());
	     
	     dist = Math.sqrt((x*x) - (y*y));
	     return dist;	     
	}
}
