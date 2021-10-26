package application;


import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.paho.client.mqttv3.MqttException;

import gps.Coordinate;
/*
 * SwingWorker che ritorna una stringa come risultato finale
 * e non produce risultati intermedi (Void)
 */
public class VeicoloWorker extends SwingWorker<String, Void> {
	private String id , p ,d;
	
	private InterfacciaVeicolo1 frame;

	private Sample sample;
	public VeicoloWorker(String id, String p, String d, InterfacciaVeicolo1 frame) {
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
			Client client=ClientBuilder.newClient();
			WebTarget endpoint=client.target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");
			TrafficService ts= null;
			try {
				sample = new Sample("tcp://137.121.170.248:30352", id, true, true );
			} catch (MqttException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int count = Thread.activeCount();
			TrafficService traffic= new TrafficService(endpoint);
			Coordinate sorg= new Coordinate (Double.parseDouble(partenza[0]),Double.parseDouble(partenza[1]));
			Coordinate dest = new Coordinate (Double.parseDouble(destinazione[0]),Double.parseDouble(destinazione[1]));
			System.out.println(sorg.toString());
			System.out.println(dest.toString());
				try {
					new Thread(new VeicoloThread(id,  sorg,dest , sample, traffic )).start();
					
				} catch (MqttException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}





			boolean flag = true;
			while(flag) {
				int count1 = Thread.activeCount();

				if(count1 == count) {
					flag=false;

				}
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