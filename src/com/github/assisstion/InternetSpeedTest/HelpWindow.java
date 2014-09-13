package com.github.assisstion.InternetSpeedTest;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HelpWindow extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 1103802825109750751L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpWindow(){
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblInternetSpeedtester = new JLabel(new ImageIcon("cat1.jpg"));
		lblInternetSpeedtester.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
		contentPane.add(lblInternetSpeedtester);

	}
}
