package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.assisstion.InternetSpeedTest.helper.TimeHelper;
import com.github.assisstion.Shared.Pair;

public class LineGraphPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -3206451660421244754L;

	private static final int TOP_Y = 30;
	private static final int BOTTOM_Y = 390;
	private static final int LEFT_X = 50;
	private static final int RIGHT_X = 920;

	private int valuesPerPoint = 1;
	private int pixelInterval = 2;
	// private int max = 0;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnMoveDisplay;
	private JButton btnUseAutomove;
	private boolean automove = true;
	private int movePosition = 0;
	private static final int LABELS = 6;
	private static final int CAP = 200;
	private static final int MARKER_LENGTH = 10;
	private static final int ADDITION = 2;

	private ArrayList<Long> ends = new ArrayList<Long>();
	private JCheckBox chckbxHideRunLines;

	protected MapHolder<Long, Double, TreeMap<Long, Double>> holder;

	/**
	 * Create the panel.
	 */
	public LineGraphPanel(MapHolder<Long, Double, TreeMap<Long, Double>> holder, boolean hasHideRunLines){
		this.holder = holder;

		setLayout(null);

		JButton btnSetValue = new JButton("Set Point Interval");
		btnSetValue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{
					valuesPerPoint = Integer.parseInt(textField.getText());
					if(valuesPerPoint <= 0){
						valuesPerPoint = 1;
					}
					repaint();
				}
				catch(NumberFormatException nfe){
					System.out.println("Input not a number!");
				}
			}
		});
		btnSetValue.setBounds(98, 0, 150, 29);
		add(btnSetValue);

		textField = new JTextField();
		textField.setText("1");
		textField.setBounds(48, 0, 40, 28);
		add(textField);
		textField.setColumns(2);

		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setBounds(543, 0, 80, 28);
		add(textField_1);
		textField_1.setColumns(10);

		btnMoveDisplay = new JButton("Set Display Position");
		btnMoveDisplay.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
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
		btnMoveDisplay.setBounds(628, 0, 170, 29);
		add(btnMoveDisplay);

		btnUseAutomove = new JButton("Use Automove");
		btnUseAutomove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				automove = true;
				repaint();
			}
		});
		btnUseAutomove.setBounds(796, 0, 130, 29);
		add(btnUseAutomove);

		chckbxHideRunLines = new JCheckBox("Hide Run Lines");
		chckbxHideRunLines.addActionListener(e -> repaint());
		chckbxHideRunLines.setBounds(260, 1, 128, 23);
		if(hasHideRunLines){
			add(chckbxHideRunLines);
		}
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(holder.getMap().size() > 0){
			g.drawString(
					"Last Update: " +
							TimeHelper.formatSystemTimeCompact(holder.getMap().lastEntry()
									.getKey()), LEFT_X + 10, TOP_Y + 20);
		}
		g.setColor(Color.BLACK);
		g.drawRect(LEFT_X, TOP_Y, getGraphWidth(), getGraphHeight());
		int size = getGraphWidth() / pixelInterval * valuesPerPoint;
		ArrayList<Pair<Double, Boolean>> points = new ArrayList<Pair<Double, Boolean>>(
				size / valuesPerPoint);
		Map.Entry<Long, Double> entry = holder.getMap().lastEntry();
		int speedStack = 0;
		int speedStackCount = 0;
		int moveAmount = 0;
		if(!automove){
			moveAmount += holder.getMap().size() - (movePosition + getGraphWidth()) /
					pixelInterval * valuesPerPoint;
			// moveAmount += movePosition;
		}
		moveAmount = moveAmount - moveAmount % valuesPerPoint + holder.getMap().size() %
				valuesPerPoint;
		if(moveAmount < 0){
			moveAmount = holder.getMap().size() % valuesPerPoint;
		}
		// System.out.println(moveAmount);
		for(int i = 0; i < moveAmount; i++){
			if(entry == null){
				break;
			}
			entry = holder.getMap().lowerEntry(entry.getKey());
		}
		int index = 0;
		int max = 0;
		for(int i = 0; i < size; i++){
			if(entry == null){
				break;
			}
			speedStack += entry.getValue();
			speedStackCount++;
			if(speedStackCount >= valuesPerPoint){
				boolean end = false;
				if(ends.size() > index &&
						ends.get(ends.size() - index - 1) > entry.getKey()){
					index++;
					end = true;
				}
				double speed = (double) speedStack / (double) speedStackCount;
				if(speed > max){
					max = (int) speed;
				}
				points.add(new Pair<Double, Boolean>(speed, end));
				speedStack = 0;
				speedStackCount = 0;
			}
			entry = holder.getMap().lowerEntry(entry.getKey());
		}
		boolean hasLast = false;
		int lastX = LEFT_X;
		int lastY = BOTTOM_Y;
		for(int i = 0; i < points.size(); i++){
			double value = points.get(points.size() - i - 1).getValueOne();
			int y = BOTTOM_Y - (int) (value * getGraphHeight() / (max + CAP));
			if(hasLast){
				g.setColor(Color.blue);
				g.drawLine(lastX, lastY, lastX + ADDITION, y);
			}
			if(!chckbxHideRunLines.isSelected()){
				if(points.get(points.size() - i - 1).getValueTwo()){
					g.setColor(Color.DARK_GRAY);
					g.drawLine(lastX + ADDITION, TOP_Y, lastX + ADDITION,
							BOTTOM_Y);
					g.setColor(Color.black);
				}
			}
			lastX = lastX + ADDITION;
			lastY = y;
			hasLast = true;
		}

		g.setColor(Color.red);
		g.drawLine(lastX + 2, TOP_Y, lastX + 2, BOTTOM_Y);
		g.setColor(Color.black);

		int off = size / LABELS;
		Map.Entry<Long, Double> entryX;
		if(entry == null){
			entryX = holder.getMap().firstEntry();
		}
		else{
			entryX = entry;
		}
		g.setColor(Color.black);

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
				entryX = holder.getMap().higherEntry(entryX.getKey());
			}
		}
		for(int j = 0; j <= max + CAP; j += markerAmount(max + CAP)){
			int y = (int) (j / (double) (max + CAP) * getGraphHeight());
			g.drawLine(LEFT_X, BOTTOM_Y - y, LEFT_X - MARKER_LENGTH, BOTTOM_Y -
					y);
			g.drawString(String.valueOf(j), LEFT_X - 32, BOTTOM_Y - y);
		}
		// g.drawString(String.valueOf(max + CAP), LEFT_X - 32, TOP_Y);

	}

	public void pushLine(long timeStamp, double speed){
		if(Double.isInfinite(speed)){
			return;
		}
		holder.getMap().put(timeStamp, speed);
		repaint();
	}

	public void runEnd(){
		ends.add(System.currentTimeMillis());
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

	public static int markerAmount(int max){
		return max / 50 * 5;
	}
}
