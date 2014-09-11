package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
	private int valuesPerPoint = 1;
	private int pixelInterval = 2;
	private int max = 0;

	/**
	 * Create the panel.
	 */
	public LineGraphPanel(){

	}

	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(LEFT_X, TOP_Y, getGraphWidth(), getGraphHeight());
		int size = getGraphWidth() / pixelInterval;
		ArrayList<Double> points = new ArrayList<Double>(size / valuesPerPoint);
		Map.Entry<Long, Double> entry = data.lastEntry();
		int speedStack = 0;
		int speedStackCount = 0;
		for(int i = 0; i < size; i++){
			if(entry == null){
				break;
			}
			speedStack += entry.getValue();
			speedStackCount++;
			if(speedStackCount >= valuesPerPoint){
				double speed = (double) speedStack / (double) speedStackCount;
				points.add(speed);
				if(speed > max){
					max = (int) speed;
				}
				speedStack = 0;
				speedStackCount = 0;
			}
			entry = data.lowerEntry(entry.getKey());
		}
		boolean hasLast = false;
		int lastX = LEFT_X;
		int lastY = BOTTOM_Y;
		for(int i = 0; i < points.size(); i++){
			double value = points.get(points.size() - i - 1);
			int y = BOTTOM_Y;
			if(hasLast){
				y = BOTTOM_Y - (int) (value * getGraphHeight() / max);
				System.out.println("X:" + (lastX + 2));
				System.out.println(y);
				g.drawLine(lastX, lastY, lastX + 2, y);
			}
			lastX = lastX + 2;
			lastY = y;
			hasLast = true;
		}
	}


	public void pushLine(long timeStamp, double speed){
		data.put(timeStamp, speed);
		repaint();
	}

	public static int getGraphWidth(){
		return RIGHT_X - LEFT_X;
	}

	public static int getGraphHeight(){
		return BOTTOM_Y - TOP_Y;
	}

	public static class Point implements Comparable<Point>{

		public int x;
		public int y;

		@Override
		public int compareTo(Point o){
			return Integer.compare(x, o.x);
		}
	}
}
