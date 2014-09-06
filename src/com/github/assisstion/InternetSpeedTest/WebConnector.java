package com.github.assisstion.InternetSpeedTest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

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
	public static List<Long> webpageByteCount(URL url, boolean silent) throws IOException{
		WebpageByteCount wbc = new WebpageByteCount(url, silent);
		wbc.execute();
		try{
			return wbc.get();
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public static class WebpageByteCount extends SwingWorker<List<Long>, Long>{

		protected URL url;
		protected boolean silent;

		private long count;
		private long startTime;

		public WebpageByteCount(URL url, boolean silent){
			this.url = url;
			this.silent = silent;
		}

		@Override
		protected List<Long> doInBackground() throws Exception{
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
					publish();
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
			//Returns the webpage's html contents
			return Arrays.asList(count, difference);
		}

		@Override
		protected void process(List<Long> longs){
			//TODO to be completed
		}




	}

}
