package application;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import gps.Coordinate;
import strada.Intersection;

public class Veicolo {
	private enum States {
		on, off, failed
	}

	private String id;
	private Coordinate sorgente;
	private Coordinate coordinates;
	private Coordinate destinazione;
	private Intersection percorso;
	
	
	public Veicolo() {

	}


	public Veicolo(String id, Coordinate coordinate) {
		this.id = id;
		this.coordinates=coordinate;
	}



	public Veicolo(String id, Coordinate sorgente, Coordinate coordinates, Coordinate destinazione, Intersection percorso) {
		this.id = id;
		this.sorgente = sorgente;
		this.coordinates = coordinates;
		this.destinazione = destinazione;
		this.percorso=percorso;
	}

	public Veicolo(String id, Coordinate sorgente, Coordinate coordinates, Coordinate destinazione) {
		this.id = id;
		this.sorgente = sorgente;
		this.coordinates = coordinates;
		this.destinazione = destinazione;
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Coordinate getSorgente() {
		return sorgente;
	}


	public void setSorgente(Coordinate sorgente) {
		this.sorgente = sorgente;
	}


	public Coordinate getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}


	public Coordinate getDestinazione() {
		return destinazione;
	}


	public void setDestinazione(Coordinate destinazione) {
		this.destinazione = destinazione;
	}


	public Intersection getPercorso() {
		return percorso;
	}


	public void setPercorso(Intersection percorso) {
		this.percorso = percorso;
	}


	public String toString() {
		return "Veicolo [id=" + id + ", sorgente=" + sorgente + ", coordinates=" + coordinates + ", destinazione="
				+ destinazione + ", percorso=" + percorso + "]";
	}

	
	

	
}