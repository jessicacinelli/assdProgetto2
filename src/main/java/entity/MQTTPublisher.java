package entity;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.json.simple.JSONObject;

import gps.Coordinate;

public class MQTTPublisher implements MqttCallback {

	private static HashMap<String,IMqttClient> clients = new HashMap<>();

	public void setClients(IMqttClient c) {
		clients.put(c.getClientId(), c);
	}

	public static boolean getClients(String id) {

		if(clients.containsKey(id))
			return true;
		else
			return false;
	}


	public static void disconnectClients() throws MqttException{
		Thread[] a = new Thread[Thread.activeCount()];
		int n = Thread.enumerate(a);

		for(IMqttClient c: clients.values()) {

			for (int i = 0; i < n; i++) {
				if (a[i].getName().equals(c.getClientId())) {
					a[i].interrupt();
					c.disconnect();
				}
			}

		}
	}

	public static void disconnectClient(IMqttClient c) throws MqttException{

		c.disconnect();
		c.close();

	}
	public MQTTPublisher() {

	}
	// Private instance variables
	private IMqttClient 			client;
	private String 				brokerUrl = "tcp://137.121.170.248:30352";
	private boolean 			quietMode = true;
	private MqttConnectOptions 	conOpt;
	private boolean 			clean = true;
	private String password = "licit";
	private String userName = "licit";

	/**
	 * Constructs an instance of the sample client wrapper
	 * @param brokerUrl the url of the server to connect to
	 * @param clientId the client id to connect with
	 * @param cleanSession clear state at end of connection or not (durable or non-durable subscriptions)
	 * @param quietMode whether debug should be printed to standard out
	 * @param userName the username to connect with
	 * @param password the password for the user
	 * @throws MqttException
	 */
	public MQTTPublisher( String clientId ) throws MqttException {

		try {
			// Construct the connection options object that contains connection parameters
			// such as cleanSession 
			conOpt = new MqttConnectOptions();
			conOpt.setCleanSession(clean);
			if(password != null ) {
				conOpt.setPassword(this.password.toCharArray());
			}
			if(userName != null) {
				conOpt.setUserName(this.userName);
			}


			try {
				client =new MqttClient(this.brokerUrl,clientId);
			}
			catch (Exception e) {
				client = new MqttClient(this.brokerUrl,MqttClient.generateClientId());
				System.out.println("eeeeeeeeeeeee");
			}


		} catch (MqttException e) {
			e.printStackTrace();
			log("Unable to set up client: "+e.toString());
			System.exit(1);
		}

		log("Connecting to "+brokerUrl + " with client ID "+client.getClientId());
		client.connect(conOpt);
		log("Connected");
	}


	/**
	 * Publish / send a message to an MQTT server
	 * @param topicName the name of the topic to publish to
	 * @param qos the quality of service to delivery the message at (0,1,2)
	 * @param payload the set of bytes to send to the MQTT server
	 * @throws MqttException
	 */
	public void publish(String topicName, int qos, byte[] payload) throws MqttException {

		// Connect to the MQTT server


		String time = new Timestamp(System.currentTimeMillis()).toString();
		log("Publishing at: "+time+ " to topic \""+topicName+"\" qos "+qos);

		// Create and configure a message
		MqttMessage message = new MqttMessage(payload);
		message.setQos(qos);

		// Send the message to the server, control is not returned until
		// it has been delivered to the server meeting the specified
		// quality of service.

		client.publish(topicName, message);
		System.out.println(message);
		// Disconnect the client

	}

	/**
	 * Subscribe to a topic on an MQTT server
	 * Once subscribed this method waits for the messages to arrive from the server
	 * that match the subscription. It continues listening for messages until the enter key is
	 * pressed.
	 * @param topicName to subscribe to (can be wild carded)
	 * @param qos the maximum quality of service to receive messages at for this subscription
	 * @throws MqttException
	 */
	public void subscribe(String topicName, int qos) throws MqttException {

		// Connect to the MQTT server
		client.connect(conOpt);
		log("Connected to "+brokerUrl+" with client ID "+client.getClientId());

		// Subscribe to the requested topic
		// The QoS specified is the maximum level that messages will be sent to the client at.
		// For instance if QoS 1 is specified, any messages originally published at QoS 2 will
		// be downgraded to 1 when delivering to the client but messages published at 1 and 0
		// will be received at the same level they were published at.
		log("Subscribing to topic \""+topicName+"\" qos "+qos);
		client.subscribe(topicName, qos);

		// Continue waiting for messages until the Enter is pressed
		log("Press <Enter> to exit");
		try {
			System.in.read();
		} catch (IOException e) {
			//If we can't read we'll just exit
		}

		// Disconnect the client from the server
		client.disconnect();
		log("Disconnected");
	}

	/**
	 * Utility method to handle logging. If 'quietMode' is set, this method does nothing
	 * @param message the message to log
	 */
	private void log(String message) {
		//if (!quietMode) {
		System.out.println(message);
		//}
	}

	/**
	 * @see MqttCallback#connectionLost(Throwable)
	 */
	public void connectionLost(Throwable cause) {
		// Called when the connection to the server has been lost.
		// An application may choose to implement reconnection
		// logic at this point. This sample simply exits.
		log("Connection to " + brokerUrl + " lost!" + cause);
		System.exit(1);
	}


	public void deliveryComplete(IMqttDeliveryToken token) {
		// Called when a message has been delivered to the
		// server. The token passed in here is the same one
		// that was passed to or returned from the original call to publish.
		// This allows applications to perform asynchronous
		// delivery without blocking until delivery completes.
		//
		// This sample demonstrates asynchronous deliver and
		// uses the token.waitForCompletion() call in the main thread which
		// blocks until the delivery has completed.
		// Additionally the deliveryComplete method will be called if
		// the callback is set on the client
		//
		// If the connection to the server breaks before delivery has completed
		// delivery of a message will complete after the client has re-connected.
		// The getPendingTokens method will provide tokens for any messages
		// that are still to be delivered.
	}

	/**
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message) throws MqttException {
		// Called when a message arrives from the server that matches any
		// subscription made by the client
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" +time +
				"  Topic:\t" + topic +
				"  Message:\t" + new String(message.getPayload()) +
				"  QoS:\t" + message.getQos());
	}

	public void disconnect() throws MqttException {
		client.disconnect();
		log("Disconnected");
	}


	public IMqttClient getClient() {
		return client;
	}


	public void setClient(IMqttClient client) {
		this.client = client;
	}


	public String getBrokerUrl() {
		return brokerUrl;
	}


	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}


	public boolean isQuietMode() {
		return quietMode;
	}


	public void setQuietMode(boolean quietMode) {
		this.quietMode = quietMode;
	}


	public MqttConnectOptions getConOpt() {
		return conOpt;
	}


	public void setConOpt(MqttConnectOptions conOpt) {
		this.conOpt = conOpt;
	}


	public boolean isClean() {
		return clean;
	}


	public void setClean(boolean clean) {
		this.clean = clean;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}



}