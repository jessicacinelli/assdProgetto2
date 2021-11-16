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




import entity.MQTTPublisher;
import entity.SensorNode;
import gps.Coordinate;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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


public class InterfaceCoordinates extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField latP;
	private JTextField lonP;
	private JTextField cP;
	private JTextField cD;
	private ArrayList<SensorNode> veicoli;
	private MQTTPublisher sample;
	private JTextField idVeicolo;
	private Thread t; 
	private Worker worker;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Runnable init = new Runnable() {
			public void run() {
				new InterfaceCoordinates();
			}
		};
		// creo la GUI nell'EDT
		SwingUtilities.invokeLater(init);
	}
	
	/**
	 * Create the frame.
	 */
	public InterfaceCoordinates() {

		this.setResizable(false);
		this.setBounds(100, 100, 450, 450);
		this.setTitle("");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.getContentPane().setLayout(null);


		/*Inserirei dati del veicolo*/
		JLabel lbl_insert = new JLabel("Inserire le coordinate di partenza");
		lbl_insert.setBounds(110, 125, 238, 13);
		this.getContentPane().add(lbl_insert);

		cP = new JTextField();
		cP.setBounds(110, 148, 242, 30);
		cP.setColumns(32);

		cD = new JTextField();
		cD.setBounds(110, 211, 242, 30);
		cD.setColumns(32);

		this.getContentPane().add(cP);
		this.getContentPane().add(cD);

		JSeparator separator = new JSeparator();
		separator.setBounds(123, 281, 200, 2);
		this.getContentPane().add(separator);

		JButton btn_conferma = new JButton("Conferma");
		btn_conferma.setBounds(173, 305, 100, 25);
		btn_conferma.addActionListener(this);
		this.getContentPane().add(btn_conferma);
		btn_conferma.setActionCommand("Conferma");

		JLabel lbl_insert_1 = new JLabel("Inserire le coordinate di destinazione");
		lbl_insert_1.setBounds(110, 188, 238, 13);
		getContentPane().add(lbl_insert_1);

		JLabel lbl_insert_2 = new JLabel("Inserire l'identificativo del veicolo");
		lbl_insert_2.setBounds(106, 62, 238, 13);
		getContentPane().add(lbl_insert_2);

		idVeicolo = new JTextField();
		idVeicolo.setColumns(32);
		idVeicolo.setBounds(106, 85, 242, 30);
		getContentPane().add(idVeicolo);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Conferma": 
			
		
		String id = idVeicolo.getText();
		String p = (cP.getText());
		String d =(cD.getText());
		String st = "";
		cD.setText(st);
		cP.setText(st);
		idVeicolo.setText(st);
		String partenza[] = p.split(",");
		String destinazione[] = d.split(",");
		if(MQTTPublisher.getClients(id)) {
			JOptionPane.showMessageDialog(this, "Monitoraggio del veicolo " + id + " gia' in corso." );
			break;
			
		}
		else {
			worker = new Worker(id, p ,d , this);
			worker.execute();
			this.dispose();
		}
		
		}
	}


}	


