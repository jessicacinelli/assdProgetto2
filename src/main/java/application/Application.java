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


public class Application {

	public static void main(String[] args) throws Exception{
		StreetController sc= new StreetControllerImpl();
		
		
		
		//Node node= new Node("10", new Coordinate(41.1597366993132, 14.50503059602507));
		
		/**while (true) {

			String topic = "node"; 
			String content = "{latitute: " + node.getCoordinates().getLatitude() + ",longitude: " + node.getCoordinates().getLongitude() +"}";
			
			//JSONObject obj=new JSONObject();
			//obj.put("id",node.getId() ); 
			//obj.put("latitude",node.getCoordinates().getLatitude());    
			//obj.put("longitude",node.getCoordinates().getLongitude());    
			//String objString = obj.toString();
			
			String broker = "tcp://127.0.0.1:1883"; 
			String clientId = "client"; 

			Publish p = new Publish(topic, broker, clientId, content);

			Coordinate  c= new Coordinate(node.getCoordinates().getLatitude() + 0.1 , node.getCoordinates().getLongitude() +0.1);
			node.setCoordinates(c);
			int scaleFactor=1;long startTime = System.currentTimeMillis();
			Thread.sleep(10000/scaleFactor);

		} */


	}}