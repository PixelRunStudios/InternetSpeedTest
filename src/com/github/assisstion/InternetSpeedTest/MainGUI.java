package com.github.assisstion.InternetSpeedTest;

import java.io.IOException;
import java.net.URL;

import com.github.assisstion.InternetSpeedTest.web.WebConnector;

public class MainGUI {
	public static void main(String[] args){
		System.out.println("Hello, world!");
		//Records program start time in nanoseconds
		long startTime = System.nanoTime();
		connect();
		//Records program end time in nanoseconds
		long endTime = System.nanoTime();
		//Calculates the difference of time in milliseconds
		double difference = (endTime - startTime) / 1000000.0;
		//Prints out the time passed in milliseconds
		System.out.println("Time passed (ms): " + difference);
		System.out.println("Goodbye, world!");
	}

	public static void connect(){
		String urlString = "http://www.apple.com";
		System.out.println("Connecting to page: " + urlString);
		System.out.println();
		try {
			URL url = new URL(urlString);
			String page = WebConnector.getWebpage(url);
			System.out.println(page);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
