package controller;



import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gps.Coordinate;

public class Node {
	private enum States {
		on, off, failed
	}

	private String id;
	private Coordinate coordinates;

	public Node() {

	}

	public Node(String id, Coordinate coordinate) {
		this.id = id;
		this.coordinates=coordinate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
}
