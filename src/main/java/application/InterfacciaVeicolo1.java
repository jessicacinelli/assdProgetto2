package application;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.catalina.Manager;
import org.eclipse.paho.client.mqttv3.MqttException;




import gps.Coordinate;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;


public class InterfacciaVeicolo1 extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField nVeicoli;
	private ArrayList<VeicoloThread> veicoli;
	private Thread t;
	private Sample sample;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfacciaVeicolo1 frame = new InterfacciaVeicolo1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfacciaVeicolo1() {

		this.setResizable(false);
		this.setBounds(100, 100, 450, 450);
		this.setTitle("");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e); 
				JOptionPane.showConfirmDialog(null,"Are sure to close!");
				ArrayList<Thread> threads = new ArrayList<Thread>();


				System.exit(1);

			}
		});
		this.getContentPane().setLayout(null);

		/* Logo */
		JLabel lbl_logo = new JLabel();
		lbl_logo.setLocation(110, 10);
		lbl_logo.setSize(231, 80);
	
		this.getContentPane().add(lbl_logo);

		/* BENVENUTO */
		JLabel lbl_benvenuto = new JLabel("MANNAGGIA A EUGENIO.!");
		lbl_benvenuto.setBounds(186, 102, 214, 13);
		this.getContentPane().add(lbl_benvenuto);

		/*Inserire il numero di veicoli da monitorare:*/
		JLabel lbl_insert = new JLabel("Inserire il numero di veicoli da monitorare:");
		lbl_insert.setBounds(114, 125, 238, 13);
		this.getContentPane().add(lbl_insert);

		nVeicoli = new JTextField();
		nVeicoli.setBounds(110, 163, 242, 30);
		nVeicoli.setColumns(32);
		this.getContentPane().add(nVeicoli);



		JSeparator separator = new JSeparator();
		separator.setBounds(123, 281, 200, 2);
		this.getContentPane().add(separator);

		JButton btn_signup = new JButton("Conferma");
		btn_signup.setBounds(173, 305, 100, 25);
		btn_signup.addActionListener(this);
		this.getContentPane().add(btn_signup);
		btn_signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		int n = Integer.parseInt(nVeicoli.getText());
		

		Client client=ClientBuilder.newClient();
		WebTarget endpoint=client.target("http://assd-traffic-service-gruppo2.router.default.svc.cluster.local/assdTrafficService/rest/");
		TrafficService ts= null;
		try {
			sample = new Sample("tcp://137.121.170.248:30352", "clientId", true, true );
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int count = Thread.activeCount();
		TrafficService traffic= new TrafficService(endpoint);
		for(int i=0; i<n; i++) {
			
			try {
				new Thread(new VeicoloThread(""+i, new Coordinate (45.6927322,4.803549) , new Coordinate(45.6927746,  4.8033378), sample, traffic )).start();
			} catch (MqttException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		


		boolean flag = true;
		while(flag) {
			int count1 = Thread.activeCount();

			if(count1 == count) {
				flag=false;

			}

		}
		System.exit(1);
	}}
