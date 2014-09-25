package com.github.assisstion.InternetSpeedTest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.assisstion.InternetSpeedTest.scheduler.RepetitionScheduler;
import com.github.assisstion.InternetSpeedTest.scheduler.WebTimedProcess;

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
	public JLabel run;
	public JLabel allSpeed;
	public JLabel allTime;
	public JLabel allKB;
	private JButton btnGetGraphs;
	public GraphWindow graphWindow;
	private HelpWindow helpWindow;
	private SettingsWindow settingsWindow;
	public JLabel timePassed;
	private JButton btnAbout;
	private JButton btnSettings;
	private WebTimedProcess wtp;
	private boolean toBeCleared;
	private boolean started = false;

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
		setTitle("Internet Speedester");
		graphWindow = new GraphWindow(this);
		helpWindow = new HelpWindow();
		settingsWindow = new SettingsWindow();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSpeed = new JLabel("Website Speed:");
		lblSpeed.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblSpeed.setBounds(18, 81, 101, 16);
		contentPane.add(lblSpeed);

		JLabel lblCumulativeSpeed = new JLabel("Cumulative Speed:");
		lblCumulativeSpeed.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblCumulativeSpeed.setBounds(16, 99, 116, 16);
		contentPane.add(lblCumulativeSpeed);

		JLabel lblCurrentWebsite = new JLabel("Current Website:");
		lblCurrentWebsite.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblCurrentWebsite.setBounds(16, 64, 116, 16);
		contentPane.add(lblCurrentWebsite);

		website = new JLabel("N/A");
		website.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		website.setBounds(169, 63, 167, 16);
		contentPane.add(website);

		speed = new JLabel("N/A");
		speed.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		speed.setBounds(169, 82, 61, 16);
		contentPane.add(speed);

		cumulativeSpeed = new JLabel("N/A");
		cumulativeSpeed.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		cumulativeSpeed.setBounds(169, 100, 61, 16);
		contentPane.add(cumulativeSpeed);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblTime.setBounds(229, 83, 42, 16);
		contentPane.add(lblTime);

		JLabel lblKb = new JLabel("KB:");
		lblKb.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblKb.setBounds(342, 83, 25, 16);
		contentPane.add(lblKb);

		JLabel label = new JLabel("Time:");
		label.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		label.setBounds(230, 100, 42, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("KB:");
		label_1.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		label_1.setBounds(342, 100, 25, 16);
		contentPane.add(label_1);

		siteTime = new JLabel("N/A");
		siteTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		siteTime.setBounds(275, 83, 61, 16);
		contentPane.add(siteTime);

		siteKB = new JLabel("N/A");
		siteKB.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		siteKB.setBounds(380, 83, 61, 16);
		contentPane.add(siteKB);

		time = new JLabel("N/A");
		time.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		time.setBounds(275, 100, 61, 16);
		contentPane.add(time);

		kb = new JLabel("N/A");
		kb.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		kb.setBounds(380, 100, 61, 16);
		contentPane.add(kb);

		JLabel lblCurrentRun = new JLabel("Current Run:");
		lblCurrentRun.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		lblCurrentRun.setBounds(16, 47, 116, 16);
		contentPane.add(lblCurrentRun);

		run = new JLabel("N/A");
		run.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		run.setBounds(169, 47, 61, 16);
		contentPane.add(run);

		JLabel lblCumulativeRunSpeed = new JLabel("Cumulative Run Speed:");
		lblCumulativeRunSpeed
		.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		lblCumulativeRunSpeed.setBounds(16, 117, 151, 16);
		contentPane.add(lblCumulativeRunSpeed);

		allSpeed = new JLabel("N/A");
		allSpeed.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		allSpeed.setBounds(169, 118, 61, 16);
		contentPane.add(allSpeed);

		JLabel lblcRunSpTime = new JLabel("Time:");
		lblcRunSpTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblcRunSpTime.setBounds(230, 117, 42, 16);
		contentPane.add(lblcRunSpTime);

		allTime = new JLabel("N/A");
		allTime.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		allTime.setBounds(275, 117, 61, 16);
		contentPane.add(allTime);

		JLabel lblCRunSpKB = new JLabel("KB:");
		lblCRunSpKB.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblCRunSpKB.setBounds(342, 117, 25, 16);
		contentPane.add(lblCRunSpKB);

		allKB = new JLabel("N/A");
		allKB.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		allKB.setBounds(380, 117, 61, 16);
		contentPane.add(allKB);

		btnGetGraphs = new JButton("Graphs");
		btnGetGraphs.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnGetGraphs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				graphWindow.setVisible(true);
			}
		});
		btnGetGraphs.setBounds(336, 10, 78, 29);
		contentPane.add(btnGetGraphs);

		JLabel lblTimePassed = new JLabel("Total Time:");
		lblTimePassed.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		lblTimePassed.setBounds(229, 47, 116, 16);
		contentPane.add(lblTimePassed);

		timePassed = new JLabel("N/A");
		timePassed.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		timePassed.setBounds(342, 47, 99, 16);
		contentPane.add(timePassed);

		btnAbout = new JButton("?");
		btnAbout.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				helpWindow.setVisible(true);
			}
		});
		btnAbout.setBounds(10, 10, 42, 29);
		contentPane.add(btnAbout);

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				settingsWindow.setVisible(true);
			}
		});
		btnSettings.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnSettings.setBounds(50, 10, 88, 29);
		contentPane.add(btnSettings);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(!started || wtp == null){
					started = true;
					new Thread(getRunnable()).start();
				}
				else{
					wtp.setPaused(false);
				}
			}
		});
		btnStart.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnStart.setBounds(142, 10, 51, 29);
		contentPane.add(btnStart);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				wtp.setPaused(true);
			}
		});
		btnStop.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnStop.setBounds(185, 10, 51, 29);
		contentPane.add(btnStop);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(wtp.siteRunning()){
					toBeCleared = true;
				}
				else{
					clear();
				}
			}
		});
		btnClear.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		btnClear.setBounds(229, 10, 61, 29);
		contentPane.add(btnClear);
	}

	public Runnable getRunnable(){
		return new Runnable(){

			@Override
			public void run(){
				try{
					wtp = new WebTimedProcess();
					wtp.gui = MainGUI.this;
					wtp.setPaused(false);
					new Thread(new RepetitionScheduler(-1, 1000, wtp, wtp))
					.start();

					/*
					 * WebProcessor wp = new
					 * WebProcessor(WebProcessor.getWebsites()); wp.gui =
					 * MainGUI.this; wp.process();
					 */
				}
				catch(Exception e){
					e.printStackTrace();
					clear();
				}
			}

		};
	}

	public void siteFinish(){
		if(toBeCleared){
			toBeCleared = false;
			clear();
		}
	}

	public WebTimedProcess getProcess(){
		return wtp;
	}

	protected void clear(){
		wtp.clear();
		graphWindow.setVisible(false);
		graphWindow = new GraphWindow(this);
		website.setText("N/A");
		speed.setText("N/A");
		cumulativeSpeed.setText("N/A");
		siteTime.setText("N/A");
		siteKB.setText("N/A");
		time.setText("N/A");
		kb.setText("N/A");
		run.setText("N/A");
		allSpeed.setText("N/A");
		allTime.setText("N/A");
		allKB.setText("N/A");
		timePassed.setText("N/A");
		started = false;
	}
}
