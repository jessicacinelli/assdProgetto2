package strada;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import gps.Coordinate;


@XmlRootElement

public class Intersection implements Serializable
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
	public Intersection(int osmid, Coordinate coordinate, ArrayList<Street> streets)
	{
		this.osmid = osmid;
		this.coordinate = coordinate;
		this.streets = streets;
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
	
	@Override
	public String toString() {
		return "Intersection [osmid=" + osmid + ", coordinate=" + coordinate + ", streets=" + streets + "]";
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
	     
	     if(x<0) x=-x;
	     if(y<0) y=-y;
	     dist = Math.sqrt((x*x) + (y*y));
	     return dist;	     
	}
}
