package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BarGraphPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -509125879716516235L;
	public static String site1;
	public static long bytes1;
	public static long loadingTime1;
	public  int counter = 0;
	public  int siteP = 0;


	/**
	 * Create the panel.
	 */
	public BarGraphPanel(){


	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect(50, 30, 200, 380);
		g.setColor(Color.black);
		g.drawRect(50, 30, 850, 360);
		for(int i = 0; i<counter;i++){
			g.drawLine(50+siteP*(i+1),390, 50+siteP*(i+1), 380);

		}

	}


	public void pushBar(String site, long bytes, long loadingTime){
		counter++;
		site1 =site;
		bytes1 = bytes;
		loadingTime1 = loadingTime;
		siteP = 850/counter;
		repaint();
	}
}
