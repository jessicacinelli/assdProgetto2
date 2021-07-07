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
		
	}
	
	public Intersection getIntersection(int osmid)
	{
		Intersection i = null;
			if (incroci.containsKey(osmid))
			{
				 i = incroci.get(osmid);
				
			}
			return i;
	}

	//Restituisce l'Incrocio più vicino
	public Intersection getNearest(double latitude, double longitude) {
		// Appichiamo l'Algoritmo per il calcolo del percorso minimo, per arrivare all'incrocio più vicino
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
	{
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
		}
	//????????????????
	}

	@Override
	public Object getShortestPath(int osmidSource, int osmidDestination, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
