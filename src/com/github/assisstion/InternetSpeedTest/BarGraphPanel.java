package com.github.assisstion.InternetSpeedTest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BarGraphPanel extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -509125879716516235L;
	public static long speed;
	public  int counter = 0;
	public int counter2 = 0;
	public int counter3 = 1;
	public  int siteP = 0;
	public int numSites = 0;

	public int topY = 30;
	public int bottomY = 390;
	public int leftX = 50;
	public int rightX = 920;

	private int markerLength = 10;
	private int distFromMarker = 2;

	private int min = 0;
	private long max = 0;
	private long cap = 200;
	int markerAmount = 0;


	public ArrayList<String> websites = new ArrayList<String>();
	public ArrayList<Long> speeds = new ArrayList<Long>();

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
		g.drawRect(leftX, topY, rightX-leftX, bottomY-topY);
		for(int i = 0; i<counter-1;i++){
			g.setColor(Color.black);
			g.drawLine(leftX+siteP*(i+1),bottomY, leftX+siteP*(i+1), bottomY+markerLength);

			System.out.println(speed);
		}
		for(int i = 0; i<counter; i++){
			g.setColor(Color.blue);
			if(speeds.get(i) != 0){
				System.out.println("hi1: "+ speeds.get(i));
				System.out.println("hi: "+(int)((bottomY-topY)/((max+cap)*1.0/speeds.get(i))));
				System.out.println("max = "+max);
				g.drawString(websites.get(i), leftX+siteP*i+distFromMarker, bottomY+20);

				g.fillRect(leftX+siteP*i+distFromMarker, bottomY - (int)((bottomY-topY)/((max+cap)*1.0/speeds.get(i))), siteP-2*distFromMarker, (int)((bottomY-topY)/((max+cap)*1.0/speeds.get(i))));
			}
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

		for(int j = 0; j<max+cap+1; j+=markerAmount){
			System.out.println("j = "+j);
			g.drawLine(leftX, (int) (topY + j*((bottomY-topY)/((max+cap)/markerAmount))), leftX + markerLength, (int)(topY + j*((bottomY-topY)/((max+cap)/markerAmount))));
			g.drawString(""+ (max+cap-j), leftX-25, (int) (topY + j*((bottomY-topY)/((max+cap)/markerAmount))));
		}
	}


	public void pushBar(String site, long bytes, long loadingTime, int sites){
		System.out.println(speeds.size());

		if(counter<sites){
			counter++;
		}
		if(loadingTime !=0){
			if(speeds.size()>counter2 || counter2 == sites){
				System.out.println("counter2: " + counter2);
				speed = (speeds.get(counter2)*counter3 + bytes/loadingTime)/(counter3+1);
			}
			else{
				speed = bytes/loadingTime;
			}

		}
		else{
			speed = 0;
		}
		System.out.println(sites);
		System.out.println(counter2);
		System.out.println(speeds.size());
		numSites = sites;
		siteP = (rightX-leftX)/counter;
		if(speeds.size()>counter2 || counter2 == sites){
			websites.set(counter2, site);
			speeds.set(counter2, speed);
		} else{
			websites.add(site);
			speeds.add(speed);
		}
		max=0;
		for(int i = 0; i<speeds.size();i++){
			long temp = speeds.get(i);
			if(temp>max){
				max = temp;
			}
		}
		repaint();
		if(counter2<sites-1){
			counter2++;
		}
		else if(counter2 ==sites-1){
			counter3++;
			counter2 = 0;
		}
		else{
			counter2 = 0;
		}
	}
}
