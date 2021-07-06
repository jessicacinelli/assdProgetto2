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
	
	
}
