package com.github.assisstion.InternetSpeedTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class GraphWindow extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 8002278492569073893L;
	private JPanel contentPane;
	public LineGraphPanel timePanel = new LineGraphPanel();
	public BarGraphPanel sitePanel = new BarGraphPanel();


	/**
	 * Create the frame.
	 */
	public GraphWindow(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("By Time", timePanel);
		tabbedPane.addTab("By Website", sitePanel);

		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				try{
					GraphWindow frame = new GraphWindow();
					frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
