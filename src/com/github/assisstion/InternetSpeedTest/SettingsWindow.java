package com.github.assisstion.InternetSpeedTest;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SettingsWindow extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 6226471022209066412L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public SettingsWindow(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
