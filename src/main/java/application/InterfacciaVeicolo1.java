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
import java.util.HashMap;
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
	private JTextField latP;
	private JTextField lonP;
	private JTextField cP;
	private JTextField cD;
	
	private Thread t;
	private Sample sample;
	private JTextField idVeicolo;
	private HashMap<String, VeicoloThread> veicoli;
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
		veicoli= new HashMap<>();
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

	
		/*Inserire il numero di veicoli da monitorare:*/
		JLabel lbl_insert = new JLabel("Inserire le coordinate di partenza");
		lbl_insert.setBounds(110, 125, 238, 13);
		this.getContentPane().add(lbl_insert);

		cP = new JTextField();
		cP.setBounds(110, 205, 242, 30);
		cP.setColumns(32);
		
		cD = new JTextField();
		cD.setBounds(110, 148, 242, 30);
		cD.setColumns(32);
		
		this.getContentPane().add(cP);
		this.getContentPane().add(cD);

		

		JSeparator separator = new JSeparator();
		separator.setBounds(123, 281, 200, 2);
		this.getContentPane().add(separator);

		JButton btn_signup = new JButton("Conferma");
		btn_signup.setBounds(173, 305, 100, 25);
		btn_signup.addActionListener(this);
		this.getContentPane().add(btn_signup);
		btn_signup.setActionCommand("Conferma");
		
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
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "Conferma":
		
		String id = idVeicolo.getText();
		if(veicoli.containsKey(id)) {
			JOptionPane.showMessageDialog(this, "Monitoraggio del veicolo " + id +" già in corso.");
			break;
		}
		String p = (cP.getText());
		String d =(cD.getText());
		String st = "";
		this.dispose();
		Home h= new Home();
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
				VeicoloThread v = new VeicoloThread(id,  sorg,dest , sample, traffic );
				veicoli.put(v.getIdVeicolo(), v);
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
		}
		
	}	
}