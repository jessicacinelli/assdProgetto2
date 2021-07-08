package gps;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement

public class Coordinate implements Serializable{
	private double latitude; 
	private double longitude;
	
	public Coordinate() {
		
	}
	public Coordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String toString() {
		return "Coordinate [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
