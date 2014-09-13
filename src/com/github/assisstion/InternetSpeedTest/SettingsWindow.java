package com.github.assisstion.InternetSpeedTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.github.assisstion.InternetSpeedTest.helper.FileHelper;

public class SettingsWindow extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 6226471022209066412L;
	private JPanel contentPane;

	public static final String FILE_LOCATION = "websites.txt";

	/**
	 * Create the frame.
	 */
	public SettingsWindow(){
		setTitle("Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 70, 301, 400);
		contentPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		textPane.setText(load(new File(FILE_LOCATION)));

		JButton btnOpen = new JButton("Load");
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane.setText(load(new File(FILE_LOCATION)));

			}
		});
		btnOpen.setBounds(148, 18, 57, 29);
		contentPane.add(btnOpen);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(202, 18, 62, 29);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String out = textPane.getText();
				save(new File(FILE_LOCATION),out);
			}
		});
		contentPane.add(btnSave);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save(new File(FILE_LOCATION),load(new File("defWebsites.txt")));
				textPane.setText(load(new File(FILE_LOCATION)));
			}
		});
		btnReset.setBounds(266, 18, 71, 29);
		contentPane.add(btnReset);

		JLabel lblNewLabel = new JLabel("Edit websites");
		lblNewLabel.setBounds(25, 23, 118, 16);
		contentPane.add(lblNewLabel);
	}

	public String load(File file){
		String in;
		try{
			in = FileHelper.read(file);
			return in;

		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void save(File file, String out){
		file = new File(FILE_LOCATION);
		try{
			FileHelper.write(file,out);
		}
		catch(IOException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
