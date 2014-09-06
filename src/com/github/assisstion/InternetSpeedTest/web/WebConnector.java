package com.github.assisstion.InternetSpeedTest.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
	 * @return a long array with two values: the number of bytes of the website and the ms passsed
	 * @throws IOException when the webpage cannot be connected to
	 */
	public static long[] webpageByteCount(URL url) throws IOException{
		//Gets the input stream to load the website
		InputStream is = url.openConnection().getInputStream();
		//Wraps the input stream with a buffer
		BufferedInputStream bis = new BufferedInputStream(is);
		//Starts the timer
		long time = System.currentTimeMillis();
		//Counts one unicode byte
		int value = bis.read();
		long count = 0;
		while(value >= 0){
			count++;
			if(count % 1000000 == 0){
				System.out.println("Counted " + count / 1000000 + " million.");
				if(count >= 100000000){
					break;
				}
			}
			//Sets the value to the next character
			value = bis.read();
		}
		long difference = System.currentTimeMillis() - time;
		//Returns the webpage's html contents
		return new long[]{count, difference};
	}
}
