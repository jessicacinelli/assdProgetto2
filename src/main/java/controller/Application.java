package controller;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;

import gps.Coordinate;


public class Application {

	public static void main(String[] args) throws Exception{

		Node node= new Node("10", new Coordinate(41.1597366993132, 14.50503059602507));
		while (true) {

			String topic = "node"; 
			String content = "{latitute: " + node.getCoordinates().getLatitude() + ",longitude: " + node.getCoordinates().getLongitude() +"}";
			JSONObject obj=new JSONObject();
			obj.put("id",node.getId() ); 
			obj.put("latitude",node.getCoordinates().getLatitude());    
			obj.put("longitude",node.getCoordinates().getLongitude());    

			String objString = obj.toString();
			int qos = 2; 
			String broker = "tcp://127.0.0.1:1883"; 
			String clientId = "client"; 

			try {

				MqttClient sampleClient = new MqttClient(broker, clientId); 
				MqttConnectOptions connOpts = new MqttConnectOptions();
				System.out.println("Connessione a broker: "+broker); 
				sampleClient.connect(connOpts); 
				System.out.println("Connessione ok"); 
				System.out.println("Invio messaggio alla coda: "+obj); 
				MqttMessage message = new MqttMessage(objString.getBytes()); 
				message.setQos(qos); 
				sampleClient.publish(topic, message); 
				System.out.println("Messaggio pubblicato");
				//sampleClient.disconnect(); 
				//	System.out.println("Disconnesso"); 
				//System.exit(0);
				Coordinate  c= new Coordinate(node.getCoordinates().getLatitude() + 0.1 , node.getCoordinates().getLongitude() +0.1);
				node.setCoordinates(c);
				int scaleFactor=1;long startTime = System.currentTimeMillis();
				Thread.sleep(10000/scaleFactor);

				long endTime = System.currentTimeMillis();
				long seconds = (endTime - startTime) / 1000;
				System.out.println("TEMPO: " + seconds);
			} catch(MqttException me) 
			{ 
				me.printStackTrace();


			}

		}
	}}