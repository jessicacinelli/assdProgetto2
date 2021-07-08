package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import gps.Coordinate;
import strada.Intersection;
import strada.Street;

public class StreetControllerImpl implements StreetController{

	private ArrayList<Coordinate> coordinate = new  ArrayList<Coordinate>();	
	private HashMap<Integer, Street> streets = new HashMap<Integer, Street>();	
	private HashMap<Integer,Intersection> incroci = new HashMap<Integer,Intersection>();
	
	public StreetControllerImpl()
	{
		coordinate.add(new Coordinate(41.1597366993132, 14.50503059602507));
		coordinate.add(new Coordinate(41.16304168304931, 14.506286303511484));
		coordinate.add(new Coordinate(41.26304168304931, 14.906286303511484));
		
		System.out.println("Coordinate");
		
		for(Coordinate c: coordinate) {
			System.out.println(c.toString());
		}
		ArrayList<Coordinate> coordinatesStreet = new ArrayList();
		coordinatesStreet.add(new Coordinate(41.1597366993132, 14.50503059602507));
		coordinatesStreet.add(new Coordinate(41.16304168304931, 14.506286303511484));
		streets.put(0, new Street(0, 0, 1, 120, 50, "Strada 0", 9, 60, coordinatesStreet));
		
		ArrayList<Coordinate> coordinatesStreet2 = new ArrayList();
		coordinatesStreet2.add(new Coordinate(41.16304168304931, 14.506286303511484));
		coordinatesStreet2.add(new Coordinate(41.26304168304931, 14.906286303511484));
		streets.put(1, new Street(1, 1, 2, 160, 50, "Strada 1", 10, 70, coordinatesStreet2));
		
		ArrayList<Coordinate> coordinatesStreet3 = new ArrayList();
		coordinatesStreet3.add(new Coordinate(41.16304168304931, 14.506286303511484));
		coordinatesStreet3.add(new Coordinate(41.66304168304931, 15.06286303511484));
		streets.put(2, new Street(2, 1, 3, 260, 50, "Strada 2", 10, 65, coordinatesStreet3));
		
		ArrayList<Coordinate> coordinatesStreet4 = new ArrayList();
		coordinatesStreet4.add(new Coordinate(41.26304168304931, 14.906286303511484));
		coordinatesStreet4.add(new Coordinate(41.86304168304931, 15.16286303511484));
		streets.put(3, new Street(3, 3, 4, 260, 50, "Strada 3", 10, 65, coordinatesStreet4));
		
		for(Street s: streets.values()) {
			System.out.println(s.toString());
		}
		
		ArrayList<Street> streetsIntersection = new ArrayList();
		streetsIntersection.add(streets.get(1));
		streetsIntersection.add(streets.get(2));
		incroci.put(00, new Intersection(00, new Coordinate (41.16304168304931, 14.506286303511484),streetsIntersection ));
		
		ArrayList<Street> streetsIntersection2 = new ArrayList();
		streetsIntersection2.add(streets.get(1));
		streetsIntersection2.add(streets.get(3));
		incroci.put(01, new Intersection(01, new Coordinate (41.26304168304931, 14.906286303511484),streetsIntersection ));

		for(Intersection i: incroci.values()) {
			System.out.println(i.toString());
		}
	}
	
	/****/
	public Intersection getIntersection(int osmid)
	{
		Intersection i = null;
			if (incroci.containsKey(osmid))
			{
				 i = incroci.get(osmid);
				
			}
			return i;
	}
/*****/
	//Restituisce l'Incrocio pi� vicino
	public Intersection getNearest(double latitude, double longitude) {
		// Appichiamo l'Algoritmo per il calcolo del percorso minimo, per arrivare all'incrocio pi� vicino
		double dist = 0;
		double mindist = 0;
		Coordinate c = new Coordinate(latitude, longitude );
		Intersection nextIncrocio = new Intersection();
		
		for(Intersection i: incroci.values())
		{
			dist = i.disgeo(c, i.getCoordinate());
		
			
			if(mindist == 0)
			{
				mindist = dist;
				nextIncrocio = i;
			}
			else if(dist < mindist)
			{
				mindist = dist;
				nextIncrocio = i;
			}
		}
		return nextIncrocio;
	}

	/*******/
	public Street getStreet(int linkId) {
		Street s = null;
		if (streets.containsKey(linkId))
		{
			 s = streets.get(linkId);
		}
		return s;
	}

	/*
	 * osmiStart -> incrocio di partenza
	 * osmiDest -> incrocio di destinazione
	 * */
	
	
	public HashMap<Integer, Street> getStreets(int osmidStart, int osmidDest) 
	{/**
		Intersection start = null;
		Intersection dest = null;

		for(Intersection i: incroci.values())
		{
			if(i.getOsmid() == osmidStart)
			{
				start = i;
			}
			else if(i.getOsmid() == osmidDest)
			{
				dest = i;
			}
		}*/
	return null;
	//????????????????
	}
	
	

	@Override
	public Object getShortestPath(int osmidSource, int osmidDestination, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
