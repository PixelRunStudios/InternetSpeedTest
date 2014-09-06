package com.github.assisstion.InternetSpeedTest;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame{

	private static final long serialVersionUID = 8479444564966231741L;

	private JPanel contentPane;
	public JLabel website;
	public JLabel speed;
	public JLabel cumulativeSpeed;
	public JLabel siteTime;
	public JLabel siteKB;
	public JLabel time;
	public JLabel kb;
	private JLabel lblCurrentRun;
	private JLabel run;
	private JLabel lblCumulativeRunSpeed;
	private JLabel label_2;
	private JLabel lblcRunSpTime;
	private JLabel cRunSpTime;
	private JLabel lblCRunSpKB;
	private JLabel CRunSpKB;
	private JButton btnGetGraphs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				try{
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 149);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSpeed = new JLabel("Website Speed:");
		lblSpeed.setBounds(21, 53, 101, 16);
		contentPane.add(lblSpeed);

		JLabel lblCumulativeSpeed = new JLabel("Cumulative Speed:");
		lblCumulativeSpeed.setBounds(19, 71, 116, 16);
		contentPane.add(lblCumulativeSpeed);

		JLabel lblCurrentWebsite = new JLabel("Current Website:");
		lblCurrentWebsite.setBounds(19, 36, 116, 16);
		contentPane.add(lblCurrentWebsite);

		website = new JLabel("N/A");
		website.setBounds(172, 35, 61, 16);
		contentPane.add(website);

		speed = new JLabel("N/A");
		speed.setBounds(172, 54, 61, 16);
		contentPane.add(speed);

		cumulativeSpeed = new JLabel("N/A");
		cumulativeSpeed.setBounds(172, 72, 61, 16);
		contentPane.add(cumulativeSpeed);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(232, 55, 42, 16);
		contentPane.add(lblTime);

		JLabel lblKb = new JLabel("KB:");
		lblKb.setBounds(345, 55, 25, 16);
		contentPane.add(lblKb);

		JLabel label = new JLabel("Time:");
		label.setBounds(233, 72, 42, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("KB:");
		label_1.setBounds(345, 72, 25, 16);
		contentPane.add(label_1);

		siteTime = new JLabel("N/A");
		siteTime.setBounds(278, 55, 61, 16);
		contentPane.add(siteTime);

		siteKB = new JLabel("N/A");
		siteKB.setBounds(383, 55, 61, 16);
		contentPane.add(siteKB);

		time = new JLabel("N/A");
		time.setBounds(278, 72, 61, 16);
		contentPane.add(time);

		kb = new JLabel("N/A");
		kb.setBounds(383, 72, 61, 16);
		contentPane.add(kb);

		lblCurrentRun = new JLabel("Current Run:");
		lblCurrentRun.setBounds(19, 19, 116, 16);
		contentPane.add(lblCurrentRun);

		run = new JLabel("N/A");
		run.setBounds(172, 19, 61, 16);
		contentPane.add(run);

		lblCumulativeRunSpeed = new JLabel("Cumulative Run Speed");
		lblCumulativeRunSpeed.setBounds(19, 89, 140, 16);
		contentPane.add(lblCumulativeRunSpeed);

		label_2 = new JLabel("N/A");
		label_2.setBounds(172, 90, 61, 16);
		contentPane.add(label_2);

		lblcRunSpTime = new JLabel("Time:");
		lblcRunSpTime.setBounds(233, 89, 42, 16);
		contentPane.add(lblcRunSpTime);

		cRunSpTime = new JLabel("N/A");
		cRunSpTime.setBounds(278, 89, 61, 16);
		contentPane.add(cRunSpTime);

		lblCRunSpKB = new JLabel("KB:");
		lblCRunSpKB.setBounds(345, 89, 25, 16);
		contentPane.add(lblCRunSpKB);

		CRunSpKB = new JLabel("N/A");
		CRunSpKB.setBounds(383, 89, 61, 16);
		contentPane.add(CRunSpKB);

		btnGetGraphs = new JButton("GET GRAPHS");
		btnGetGraphs.setBounds(301, 16, 117, 29);
		contentPane.add(btnGetGraphs);
	}


	public static void test(){
		System.out.println("Hello, world!");
		//Records program start time in nanoseconds
		long startTime = System.nanoTime();
		connect();
		//Records program end time in nanoseconds
		long endTime = System.nanoTime();
		//Calculates the difference of time in milliseconds
		double difference = (endTime - startTime) / 1000000.0;
		//Prints out the time passed in milliseconds
		System.out.println("Time passed (ms): " + difference);
		System.out.println("Goodbye, world!");
	}

	public static void connect(){
		String urlString = "http://www.apple.com";
		System.out.println("Connecting to page: " + urlString);
		System.out.println();
		try {
			URL url = new URL(urlString);
			String page = WebConnector.getWebpage(url);
			System.out.println(page);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
