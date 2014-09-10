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


	/**
	 * Create the panel.
	 */
	public BarGraphPanel(){


	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(50, 30, 200, 380);
		g.setColor(Color.black);
		g.drawLine(50, 390, 700, 390);
		g.drawLine(50, 30, 50, 390);

	}


	public void pushBar(String site, long bytes, long loadingTime){
		site1 =site;
		bytes1 = bytes;
		loadingTime1 = loadingTime;
		repaint();
	}
}
