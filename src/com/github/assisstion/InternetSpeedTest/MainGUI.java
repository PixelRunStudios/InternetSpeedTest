package com.github.assisstion.InternetSpeedTest;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSpeed = new JLabel("Website Speed:");
		lblSpeed.setBounds(6, 36, 101, 16);
		contentPane.add(lblSpeed);

		JLabel lblCumulativeSpeed = new JLabel("Cumulative Speed:");
		lblCumulativeSpeed.setBounds(6, 64, 116, 16);
		contentPane.add(lblCumulativeSpeed);

		JLabel lblCurrentWebsite = new JLabel("Current Website:");
		lblCurrentWebsite.setBounds(6, 8, 116, 16);
		contentPane.add(lblCurrentWebsite);

		website = new JLabel("N/A");
		website.setBounds(146, 8, 61, 16);
		contentPane.add(website);

		speed = new JLabel("N/A");
		speed.setBounds(146, 36, 61, 16);
		contentPane.add(speed);

		cumulativeSpeed = new JLabel("N/A");
		cumulativeSpeed.setBounds(146, 64, 61, 16);
		contentPane.add(cumulativeSpeed);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(219, 36, 42, 16);
		contentPane.add(lblTime);

		JLabel lblKb = new JLabel("KB:");
		lblKb.setBounds(345, 36, 25, 16);
		contentPane.add(lblKb);

		JLabel label = new JLabel("Time:");
		label.setBounds(219, 64, 42, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("KB:");
		label_1.setBounds(345, 64, 25, 16);
		contentPane.add(label_1);

		siteTime = new JLabel("N/A");
		siteTime.setBounds(264, 36, 61, 16);
		contentPane.add(siteTime);

		siteKB = new JLabel("N/A");
		siteKB.setBounds(383, 36, 61, 16);
		contentPane.add(siteKB);

		time = new JLabel("N/A");
		time.setBounds(264, 64, 61, 16);
		contentPane.add(time);

		kb = new JLabel("N/A");
		kb.setBounds(383, 64, 61, 16);
		contentPane.add(kb);
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
