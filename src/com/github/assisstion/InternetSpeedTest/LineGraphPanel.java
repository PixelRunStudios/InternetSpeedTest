package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.assisstion.InternetSpeedTest.helper.TimeHelper;

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
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnMoveDisplay;
	private JButton btnUseAutomove;
	private boolean automove = true;
	private int movePosition = 0;
	private static final int LABELS = 6;

	/**
	 * Create the panel.
	 */
	public LineGraphPanel(){
		setLayout(null);

		JButton btnSetValue = new JButton("Set Point Interval");
		btnSetValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					valuesPerPoint = Integer.parseInt(textField.getText());
					repaint();
				}
				catch(NumberFormatException nfe){
					System.out.println("Input not a number!");
				}
			}
		});
		btnSetValue.setBounds(50, 0, 150, 29);
		add(btnSetValue);

		textField = new JTextField();
		textField.setText("1");
		textField.setBounds(0, 0, 40, 28);
		add(textField);
		textField.setColumns(2);

		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setBounds(215, 0, 80, 28);
		add(textField_1);
		textField_1.setColumns(10);

		btnMoveDisplay = new JButton("Set Display Position");
		btnMoveDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					automove = false;
					movePosition = Integer.parseInt(textField_1.getText());
					repaint();
				}
				catch(NumberFormatException nfe){
					System.out.println("Input not a number!");
				}
			}
		});
		btnMoveDisplay.setBounds(300, 0, 180, 29);
		add(btnMoveDisplay);

		btnUseAutomove = new JButton("Use Automove");
		btnUseAutomove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				automove = true;
				repaint();
			}
		});
		btnUseAutomove.setBounds(500, 0, 150, 29);
		add(btnUseAutomove);

	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(LEFT_X, TOP_Y, getGraphWidth(), getGraphHeight());
		int size = getGraphWidth() / pixelInterval * valuesPerPoint;
		ArrayList<Double> points = new ArrayList<Double>(size / valuesPerPoint);
		Map.Entry<Long, Double> entry = data.lastEntry();
		int speedStack = 0;
		int speedStackCount = 0;
		int moveAmount = 0;
		if(!automove){
			moveAmount += data.size() -
					(movePosition + getGraphWidth()) / pixelInterval * valuesPerPoint;
			//moveAmount += movePosition;
		}
		moveAmount = moveAmount - moveAmount % valuesPerPoint +
				data.size() % valuesPerPoint;
		if(moveAmount < 0){
			moveAmount = data.size() % valuesPerPoint;
		}
		//System.out.println(moveAmount);
		for(int i = 0; i < moveAmount; i++){
			if(entry == null){
				break;
			}
			entry = data.lowerEntry(entry.getKey());
		}
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
				g.drawLine(lastX, lastY, lastX + 2, y);
			}
			lastX = lastX + 2;
			lastY = y;
			hasLast = true;
		}
		int off = size / LABELS;
		Map.Entry<Long, Double> entryX;
		if(entry == null){
			entryX = data.firstEntry();
		}
		else{
			entryX = entry;
		}
		out: for(int i = 0; i < LABELS; i++){
			if(entryX == null){
				break out;
			}
			int x = LEFT_X + getGraphWidth() / LABELS * i;
			g.drawLine(x, BOTTOM_Y, x, BOTTOM_Y + 15);
			g.drawLine(x + 1, BOTTOM_Y, x + 1, BOTTOM_Y + 15);
			g.drawString(TimeHelper.formatSystemTimeCompact(entryX.getKey()),
					x, BOTTOM_Y + 30);
			for(int j = 0; j < off; j++){
				if(entryX == null){
					break out;
				}
				entryX = data.higherEntry(entryX.getKey());
			}
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
