package application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import entity.MQTTPublisher;
import gps.Coordinate;

public class Home extends JFrame  implements ActionListener {

	private JPanel contentPane;
	private JButton btn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		this.setResizable(false);
		this.setBounds(100, 100, 450, 450);
		this.setTitle("");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().setBackground(Color.black);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e); 
				int conf= JOptionPane.showConfirmDialog(null,"Conferma", null, JOptionPane.YES_NO_CANCEL_OPTION);
				if(conf==0)
				exit();

			}

			
		});
		this.getContentPane().setLayout(null);

		/* Logo */
		JLabel lbl_logo = new JLabel();
		lbl_logo.setLocation(96, 32);
		lbl_logo.setSize(254, 206);
		lbl_logo.setIcon(new ImageIcon("./img.jpg"));
		this.getContentPane().add(lbl_logo);


		btn = new JButton("Monitora un veicolo");
		btn.setBounds(145, 267, 147, 25);
		btn.addActionListener(this);
		this.getContentPane().add(btn);
		btn.setActionCommand("Monitora");
		
		JButton exit = new JButton("Esci");
		exit.setBounds(168, 320, 100, 25);
		exit.addActionListener(this);
		this.getContentPane().add(exit);
		exit.setActionCommand("Esci");
		
		
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "Monitora":
			InterfaceCoordinates i = new InterfaceCoordinates();
			break;
			
		case "Esci": 
			exit();
			
		
	}
	}
	public void exit() {
		try {
			MQTTPublisher.disconnectClients();
			
		} catch (MqttException e1) {
			
			e1.printStackTrace();
		}
		
			this.dispose();
		System.exit(0);
	}
		
	}
