package application;



import javax.swing.*;

import org.eclipse.paho.client.mqttv3.MqttException;

import entity.MQTTPublisher;
import entity.SensorNode;
import gps.Coordinate;
import rest.TrafficService;
/*
 * SwingWorker che ritorna una stringa come risultato finale
 * e non produce risultati intermedi (Void)
 */
public class Worker extends SwingWorker<String, Void> {
	private String id , p ,d;
	
	private InterfaceCoordinates frame;

	private MQTTPublisher mqttPublisher;
	public Worker(String id, String p, String d, InterfaceCoordinates frame) {
		this.id = id;
		this.p=p;
		this.d=d;
		this.frame=frame;
	}
	@Override
	protected String doInBackground() throws Exception {
		
		if (!isCancelled()) {
			String partenza[] = p.split(",");
			String destinazione[] = d.split(",");
			TrafficService ts= null;
			try {
				mqttPublisher = new MQTTPublisher( id );
				mqttPublisher.setClients(mqttPublisher.getClient());
			} catch (MqttException e1) {
				e1.printStackTrace();
			}

			int count = Thread.activeCount();
			TrafficService traffic= new TrafficService();
			Coordinate sorg= new Coordinate (Double.parseDouble(partenza[0]),Double.parseDouble(partenza[1]));
			Coordinate dest = new Coordinate (Double.parseDouble(destinazione[0]),Double.parseDouble(destinazione[1]));
			System.out.println(sorg.toString());
			System.out.println(dest.toString());
				try {
					Thread t= new Thread(new SensorNode(id,  sorg, dest , mqttPublisher, traffic ));
					t.setName(id);
					t.start();
					
				} catch (MqttException e1) {
					e1.printStackTrace();
				}
				return "" ;
		} else {
			return "";
		}
	}
	@Override
	protected void done() { // chiamato dopo doInBackground ed eseguito dall'EDT
		
		
	}
}