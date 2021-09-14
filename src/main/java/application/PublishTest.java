package application;

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

import controller.StreetController;
import controller.StreetControllerImpl;
import gps.Coordinate;


public class PublishTest {

	public static void main(String[] args) throws Exception{
		StreetController sc= new StreetControllerImpl();
		
		
		
		Veicolo v= new Veicolo("10", new Coordinate(41.1597366993132, 14.50503059602507));
		String topic = "node"; 
		String broker = "tcp://127.0.0.1:1883"; 
		String clientId = "client";
		Publish p;
		while (true) {

			
			String content = "{latitute: " + v.getCoordinates().getLatitude() + ",longitude: " + v.getCoordinates().getLongitude() +"}";
			
			
			/*JSONObject obj=new JSONObject();
			obj.put("id",v.getId() ); 
			obj.put("latitude",v.getCoordinates().getLatitude());    
			obj.put("longitude",v.getCoordinates().getLongitude());    
			String objString = obj.toString();
			System.out.println("JSON" + obj);*/
			
			p = new Publish(topic, broker, clientId, content);

			Coordinate  c= new Coordinate(v.getCoordinates().getLatitude() + 0.1 , v.getCoordinates().getLongitude() +0.1);
			v.setCoordinates(c);
			
			int scaleFactor=1;
			int period=10000;
			Thread.sleep(period/scaleFactor);

		} 


	}}