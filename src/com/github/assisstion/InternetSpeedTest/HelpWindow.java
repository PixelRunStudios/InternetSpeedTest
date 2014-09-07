package com.github.assisstion.InternetSpeedTest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class HelpWindow extends JFrame{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				try{
					HelpWindow frame = new HelpWindow();
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
	public HelpWindow(){
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 287, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInternetSpeedtester = new JLabel("Internet Speedtester");
		lblInternetSpeedtester.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblInternetSpeedtester.setBounds(80, 50, 132, 16);
		contentPane.add(lblInternetSpeedtester);

		JLabel lblV = new JLabel("v1.0");
		lblV.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		lblV.setBounds(125, 75, 34, 16);
		contentPane.add(lblV);

		JTextPane txtpnHi = new JTextPane();
		txtpnHi.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		txtpnHi.setBackground(SystemColor.window);
		txtpnHi.setText("This is a simple program that tests your wifi speed by accessing the homepages of some of the most commonly accesse websites with servers in the US. You may change the list in the settings window.");
		txtpnHi.setBounds(33, 123, 219, 130);
		contentPane.add(txtpnHi);

		JLabel lblMarkusFeng = new JLabel("© Markus Feng and Alex Guo");
		lblMarkusFeng.setFont(new Font("Helvetica Neue", Font.PLAIN, 9));
		lblMarkusFeng.setBounds(79, 98, 188, 16);
		contentPane.add(lblMarkusFeng);
	}
}
