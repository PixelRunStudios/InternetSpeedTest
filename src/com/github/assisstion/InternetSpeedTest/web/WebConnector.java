package com.github.assisstion.InternetSpeedTest.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import com.github.assisstion.InternetSpeedTest.MainGUI;
import com.github.assisstion.Shared.Pair;

public class WebConnector {


	/**
	 * Gets the HTML representation of a webpage as a String
	 * @param url the URL of the webpage
	 * @return the HTML representation of the webpate
	 * @throws IOException when the webpage cannot be connected to
	 */
	public static String getWebpage(URL url) throws IOException{
		//Gets the input stream to load the website
		InputStream is = url.openConnection().getInputStream();
		//Wraps the input stream with a buffer
		BufferedInputStream bis = new BufferedInputStream(is);
		//Gives the int value of one unicode character
		int value = bis.read();
		String output = "";
		while(value >= 0){
			//Adds the unicode character to the output
			output += (char) value;
			//Sets the value to the next character
			value = bis.read();
		}
		//Returns the webpage's html contents
		return output;
	}

	/**
	 * Gets the number of bytes of a webpage
	 * @param url the URL of the webpage
	 * @param silent if printing is allowed
	 * @return a long array with two values: the number of bytes of the website and the ms passsed
	 * @throws IOException when the webpage cannot be connected to
	 */
	public static Pair<Long, Long> webpageByteCount(URL url, boolean silent, MainGUI gui,
			Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders, int attemptCount) throws IOException{
		WebpageByteCount wbc = new WebpageByteCount(url, silent, gui, senders, attemptCount);
		wbc.execute();
		try{
			return wbc.get();
		}
		catch(Exception e){
			throw new IOException(e);
		}
	}

	public static class WebpageByteCount extends SwingWorker<Pair<Long, Long>, Pair<Long, Long>>{

		protected int attemptCount;
		protected URL url;
		protected boolean silent;
		protected MainGUI gui;
		protected Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders;

		private long count;
		private long startTime;

		public WebpageByteCount(URL url, boolean silent, MainGUI gui,
				Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders, int attemptCount){
			this.url = url;
			this.silent = silent;
			this.gui = gui;
			this.senders = senders;
			this.attemptCount = attemptCount;
		}

		@Override
		protected Pair<Long, Long> doInBackground() throws Exception{
			//Gets the input stream to load the website
			InputStream is = url.openConnection().getInputStream();
			//Wraps the input stream with a buffer
			BufferedInputStream bis = new BufferedInputStream(is);
			//Starts the timer
			startTime = System.currentTimeMillis();
			//Counts one unicode byte
			int value = bis.read();
			count = 0;
			while(value >= 0){
				count++;
				if(count % 1000 == 0){
					publish(new Pair<Long, Long>(count, System.currentTimeMillis()));
					if(count % 1000000 == 0){
						if(!silent){
							System.out.println("Counted " + count / 1000000 + " million.");
						}
						if(count >= 100000000){
							break;
						}
					}
				}
				//Sets the value to the next character
				value = bis.read();
			}
			long difference = System.currentTimeMillis() - startTime;
			return new Pair<Long, Long>(count, difference);
		}

		@Override
		protected void process(List<Pair<Long, Long>> pairs){
			if(gui == null){
				return;
			}
			Pair<Long, Long> pair = pairs.get(pairs.size() - 1);
			long bytes = pair.getValueOne();
			long time = pair.getValueTwo() - startTime;
			gui.siteKB.setText(String.valueOf(bytes / 1000));
			gui.siteTime.setText(String.valueOf(time / 1000.0));
			gui.speed.setText(String.valueOf((double) bytes / (double) time));
			for(InfoSender<Pair<Pair<Long, Long>, Integer>> sender : senders){
				sender.send(new Pair<Pair<Long, Long>, Integer>(new Pair<Long, Long>(bytes, time), attemptCount));
			}

		}




	}

}
