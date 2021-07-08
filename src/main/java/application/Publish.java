package application;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;

import gps.Coordinate;

public class Publish {
	
	public Publish(String topic, String broker, String client, String message) {
		
	try {

		MqttClient sampleClient = new MqttClient(broker, client); 
		MqttConnectOptions connOpts = new MqttConnectOptions();
		System.out.println("Connessione a broker: "+broker); 
		sampleClient.connect(connOpts); 
		System.out.println("Connessione ok"); 
		System.out.println("Invio messaggio alla coda: "+message); 
		MqttMessage msg = new MqttMessage(message.getBytes()); 
		//message.setQos(qos); 
		sampleClient.publish(topic, msg); 
		System.out.println("Messaggio pubblicato");
		

	} catch(MqttException me) 
	{ 
		me.printStackTrace();


}}}
