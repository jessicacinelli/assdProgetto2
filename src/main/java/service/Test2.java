package application;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.paho.client.mqttv3.MqttException;

import gps.Coordinate;

public class Test2 {

	public static void main(String[] args) throws MqttException, InterruptedException {
		Client client=ClientBuilder.newClient();
		WebTarget endpoint=client.target("http://assd-traffic-service-progetto2.router.default.svc.cluster.local/assdTrafficService/rest/");
		
		TrafficService ts= new TrafficService(endpoint);

		Coordinate sorgente1= new Coordinate (45.6927322,4.803549);
		Coordinate corrente1= new Coordinate (45.6927322,4.803549);
		Coordinate destinazione1= new Coordinate(45.6931033,4.7952858);
		
		Veicolo v2= new Veicolo("11",sorgente1, corrente1, destinazione1 );
		String topic = "node/+/coordinates"; 
		String broker = "tcp://127.0.0.1:1883"; 
		String clientId = "client1";
		int qos=2;
		
		Sample sample = new Sample(broker, clientId, true, false);
		Application a= new Application(topic, qos);
		
		a.run(v2, ts, sample);

	}

}
