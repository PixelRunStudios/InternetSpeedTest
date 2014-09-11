package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JPanel;

public class LineGraphPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -3206451660421244754L;

	private static final int TOP_Y = 30;
	private static final int BOTTOM_Y = 390;
	private static final int LEFT_X = 50;
	private static final int RIGHT_X = 920;

	private TreeMap<Long, Double> data = new TreeMap<Long, Double>();
	private long timeDifference = getGraphWidth() / 10;
	private long leftTime = 0;
	private int pixelInterval = 2;

	private TreeSet<Line> lines = new TreeSet<Line>();

	/**
	 * Create the panel.
	 */
	public LineGraphPanel(){

	}

	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(LEFT_X, TOP_Y, getGraphWidth(), getGraphHeight());
		Graphics2D g2d = (Graphics2D) g;
		for(Map.Entry<Long, Double> entry : data.entrySet()){
			//TODO
		}
		for(Line line : lines){
			//TODO
		}
	}


	public void pushLine(long timeStamp, double speed){
		data.put(timeStamp, speed);
	}

	public static int getGraphWidth(){
		return RIGHT_X - LEFT_X;
	}

	public static int getGraphHeight(){
		return BOTTOM_Y - TOP_Y;
	}

	public static class Line implements Comparable<Line>{

		public int x1;
		public int y1;
		public int x2;
		public int y2;

		@Override
		public int compareTo(Line o){
			return Integer.compare(x1, o.x1);
		}
	}
}
