package com.github.assisstion.InternetSpeedTest.web;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.assisstion.InternetSpeedTest.MainGUI;
import com.github.assisstion.InternetSpeedTest.helper.FileHelper;
import com.github.assisstion.InternetSpeedTest.helper.MathHelper;
import com.github.assisstion.InternetSpeedTest.scheduler.WebTimedProcess;
import com.github.assisstion.Shared.Pair;

public class WebProcessor implements InfoSender<Pair<Pair<Long, Long>, Integer>>{


	protected Map<String, String> data;

	//Time in ms
	protected int attemptCount = 0;
	protected long totalTime = 0;
	protected long totalBytes = 0;
	protected int success = 0;
	protected int counter = 0;
	protected boolean https = false;
	protected int failedAttempts = 0;
	public boolean silent = false;
	public ArrayList<Long> bytes = new ArrayList<Long>();
	public ArrayList<Long> time = new ArrayList<Long>();
	public MainGUI gui;
	private String currentName = "N/A";
	private boolean done = false;
	public WebTimedProcess timer;

	public static void main(String[] args){
		WebProcessor wp = new WebProcessor(getWebsites());
		wp.process();
	}

	public static Map<String, String> getWebsites(){
		Map<String, String> out = new LinkedHashMap<String, String>();
		try{
			//Insert your own file here
			String in = FileHelper.read(new File("websites.txt"));
			String[] inArray = in.split("\n");
			for(String part : inArray){
				String[] partArray = part.split("\t");
				out.put(partArray[0], partArray[1]);
			}
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	public WebProcessor(Map<String, String> map){
		data = map;
	}

	public WebProcessor(){
		this(getWebsites());
	}


	public void process(){
		done = false;
		attemptCount = 0;
		currentName = "N/A";
		bytes.clear();
		time.clear();
		totalTime = 0;
		totalBytes = 0;
		counter = 0;
		https = false;
		failedAttempts = 0;
		if(!silent){
			System.out.println("Start processing...");
		}
		BufferedWriter writer = null;
		try{
			try{
				new File("data").mkdir();
				writer = new BufferedWriter(new FileWriter(new File("data/output.txt")));
			}
			catch(IOException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(Map.Entry<String, String> entry : data.entrySet()){
				boolean completelyFailed = false;
				String newValue = null;
				counter++;
				if(!processSite(entry.getKey(), entry.getValue())){
					newValue = "https://" + entry.getValue().substring(7);
					https = true;
					failedAttempts++;
					completelyFailed = !processSite(entry.getKey(), newValue);
					success += completelyFailed ? 0 : 1;
					https = false;
				}
				else{
					success++;
				}
				if(!completelyFailed){
					String string = "";
					string += entry.getKey() + "\t"+ (newValue == null ? entry.getValue() : newValue) + "\t" + bytes.get(bytes.size() - 1) + "\t" + time.get(bytes.size() - 1) + "\n";

					try{
						writer.write(string);
					}
					catch(IOException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				//Waits for 1 minute before stopping
				if(totalTime > 60000){
					break;
				}
			}
		}
		finally{
			if(writer != null){
				try{
					writer.close();
				}
				catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		done = true;
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run(){
				if(gui != null){
					gui.website.setText("N/A");
					gui.speed.setText("N/A");
					gui.siteKB.setText("N/A");
					gui.siteTime.setText("N/A");
				}
			}

		});
		if(!silent){
			System.out.println();
			System.out.println("Done processing!");
			System.out.println("Amount of total websites: "+ success + " out of " + counter);
			System.out.println("Amount of websites switching to https://: "+failedAttempts);
			System.out.println("Total Bytes: " + totalBytes);
			System.out.println("Total Time (ms): " + totalTime);
			System.out.println("Average Speed (KB/s): " + (double) totalBytes / (double) totalTime);
			/*for(int i = 0; i<bytes.size();i++){
				System.out.println(i);
				System.out.println(bytes.get(i));
				System.out.println(time.get(i));
				System.out.println();
			}*/
		}



	}

	public long getTotalTime(){
		return totalTime;
	}

	public long getTotalBytes(){
		return totalBytes;
	}

	public boolean processSite(String name, String website){
		attemptCount++;
		try{
			if(!https){
				if(!silent){
					System.out.println();
				}
			}
			if(!silent){
				System.out.println(counter + (https ? "B" : "A") +
						". Trying website: " + name);
			}
			currentName = name;
			URL url = new URL(website);
			HashSet<InfoSender<Pair<Pair<Long, Long>, Integer>>> set = new HashSet<InfoSender<Pair<Pair<Long, Long>, Integer>>>();
			set.add(this);
			Pair<Long, Long> total =  WebConnector.webpageByteCount(url, silent, gui, set, attemptCount);
			synchronized(this){
				totalBytes += total.getValueOne();
				totalTime += total.getValueTwo();
			}

			if(total.getValueOne() == 0){
				return false;
			}
			if(!silent){
				System.out.println("Bytes: " + total.getValueOne());
				System.out.println("Time (ms): " + total.getValueTwo());
				System.out.println("Speed (KB/s): " + (double) total.getValueOne() / (double) total.getValueTwo());

			}
			bytes.add(total.getValueOne());
			time.add(total.getValueTwo());
			return true;
		}
		catch(IOException e){
			if(!silent){
				System.out.println("Site unavailable! Trying next one.");
				System.out.println();
			}
			return false;
		}
	}

	@Override
	public void send(Pair<Pair<Long, Long>, Integer> infox){
		if(done || attemptCount != infox.getValueTwo()){
			return;
		}
		Pair<Long, Long> info = infox.getValueOne();
		long bytes;
		long time;
		synchronized(this){
			bytes = totalBytes + info.getValueOne();
			time = totalTime + info.getValueTwo();
		}
		gui.website.setText(currentName + "  ("+ counter +"/"+data.size()+")");
		gui.kb.setText(String.valueOf(bytes / 1000));
		gui.time.setText(String.valueOf(time / 1000.0));
		gui.cumulativeSpeed.setText(String.valueOf(MathHelper.roundThreeDecimals((double) bytes / (double) time)));
		if(timer != null){
			timer.send(new Pair<Long, Long>(bytes, time));
		}
	}
}
