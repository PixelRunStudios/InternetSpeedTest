package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.assisstion.Shared.Pair;

public class BarGraphPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -509125879716516235L;
	public int xElements = 0;
	public int currentXElement = 0;
	public int numberRuns = 1;
	public int siteP = 0;
	public int numSites = 0;

	public static final int TOP_Y = 30;
	public static final int BOTTOM_Y = 390;
	public static final int LEFT_X = 50;
	public static final int RIGHT_X = 920;

	private static final int MARKER_LENGTH = 10;
	private static final int DIST_FROM_MARKER = 2;

	//private int min = 0;
	private long max = 0;
	private long cap = 200;
	int markerAmount = 0;


	public LinkedHashMap<String, Pair<Long, Long>> speeds = new LinkedHashMap<String, Pair<Long, Long>>();

	/**
	 * Create the panel.
	 */
	public BarGraphPanel(){
		setLayout(null);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 10, 61, 16);
		add(lblNewLabel);


	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect(50, 30, 200, 380);
		g.setColor(Color.black);
		g.drawRect(LEFT_X, TOP_Y, RIGHT_X-LEFT_X, BOTTOM_Y-TOP_Y);
		for(int i = 0; i<xElements-1;i++){
			g.setColor(Color.black);
			g.drawLine(LEFT_X+siteP*(i+1),BOTTOM_Y, LEFT_X+siteP*(i+1), BOTTOM_Y+MARKER_LENGTH);

		}
		int i = 0;
		for(Map.Entry<String, Pair<Long, Long>> entry : speeds.entrySet()){
			g.setColor(Color.blue);

			//System.out.println("hi1: "+ speeds.get(i));
			//System.out.println("hi: "+(int)((BOTTOM_Y-TOP_Y)/((max+cap)*1.0/speeds.get(i))));
			//System.out.println("max = "+max);
			Graphics2D g2d = (Graphics2D) g;
			//AffineTransform form = new AffineTransform();
			//form.rotate(-Math.PI / 2);
			//Font font = g2d.getFont();
			//Font x = font.deriveFont(form);
			//g2d.setFont(x);
			g2d.setColor(Color.BLACK);
			AffineTransform form = new AffineTransform();
			form.rotate(-Math.PI / 2, getWidth()/2, getHeight()/2);
			g2d.transform(form);
			//g2d.drawString(entry.getKey(), LEFT_X+siteP*i+DIST_FROM_MARKER, BOTTOM_Y+20);
			g2d.drawString(entry.getKey(), BOTTOM_Y+20-150, LEFT_X+siteP*i+DIST_FROM_MARKER - 205);
			try{
				g2d.transform(form.createInverse());
			}
			catch(NoninvertibleTransformException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g2d.setColor(Color.BLUE);
			//g2d.setFont(font);
			if(entry.getValue().getValueOne() != 0 && entry.getValue().getValueTwo() != 0){
				long speedNow = entry.getValue().getValueOne() / entry.getValue().getValueTwo();

				g.fillRect(LEFT_X+siteP*i+DIST_FROM_MARKER, BOTTOM_Y - (int)((BOTTOM_Y-TOP_Y)/((double)(max+cap)/speedNow)), siteP-2*DIST_FROM_MARKER, (int)((BOTTOM_Y-TOP_Y)/((double)(max+cap)/speedNow)));
			}
			i++;
		}
		if(max+cap >0 && max+ cap <=25){
			markerAmount = 5;
		}
		else if(max+cap >25 && max+ cap <=100){
			markerAmount = 5;
		}
		else if(max+cap >100 && max+ cap <=250){
			markerAmount = 25;
		}
		else if(max+cap >250 && max+ cap <=500){
			markerAmount = 50;
		}
		else if(max+cap >500 && max+ cap <=1000){
			markerAmount = 100;
		}
		else if(max+cap >1000 && max+ cap <=2000){
			markerAmount = 200;
		}
		else if(max+cap >2000 && max+ cap <=5000){
			markerAmount = 500;
		}
		else if(max+cap >5000 && max+ cap <=10000){
			markerAmount = 1000;
		}
		else if(max+cap >10000){
			markerAmount = 2000;
		}
		g.setColor(Color.black);

		int counter = 0;
		for(int j = 0; j<max+cap+1; j+=markerAmount){
			//System.out.println("mc = " + (max + cap));
			//System.out.println("j = " + j);
			g.drawLine(LEFT_X, (int) (TOP_Y + j*((BOTTOM_Y-TOP_Y)/((max+cap)/markerAmount))), LEFT_X + MARKER_LENGTH, (int)(TOP_Y + j*((BOTTOM_Y-TOP_Y)/((max+cap)/markerAmount))));
			g.drawString(""+ (max+cap-j), LEFT_X-25, (int) (TOP_Y + counter++*((BOTTOM_Y-TOP_Y)/((double)(max+cap)/markerAmount))));
		}
	}


	public void pushBar(String site, long bytes, long loadingTime, int sites){
		//System.out.println(speeds.size());

		if(xElements<sites){
			xElements++;
		}
		if(speeds.get(site) != null){
			//System.out.println("counter2: " + currentXElement);
			Pair<Long, Long> pair = speeds.get(site);
			speeds.put(site, new Pair<Long, Long>(
					pair.getValueOne() + bytes, pair.getValueTwo() + loadingTime));
		}
		else{
			speeds.put(site, new Pair<Long, Long>(
					bytes, loadingTime));
		}
		//System.out.println(sites);
		//System.out.println(currentXElement);
		//System.out.println(speeds.size());
		numSites = sites;
		siteP = (RIGHT_X-LEFT_X)/xElements;
		max=0;
		for(Map.Entry<String, Pair<Long, Long>> entry : speeds.entrySet()){
			Pair<Long, Long> pair = entry.getValue();
			if(pair.getValueTwo() == 0){
				continue;
			}
			long temp = pair.getValueOne() / pair.getValueTwo();
			if(temp>max){
				max = temp;
			}
		}
		repaint();
		if(currentXElement<sites-1){
			currentXElement++;
		}
		else if(currentXElement ==sites-1){
			numberRuns++;
			currentXElement = 0;
		}
		else{
			currentXElement = 0;
		}

	}
}
