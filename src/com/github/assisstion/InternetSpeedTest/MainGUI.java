package com.github.assisstion.InternetSpeedTest;

import java.io.IOException;
import java.net.URL;

import com.github.assisstion.InternetSpeedTest.web.WebConnector;

public class MainGUI {
	public static void main(String[] args){
		System.out.println("Hello, world!");
		connect();
		System.out.println("Goodbye, world!");
	}
	
	public static void connect(){
		String urlString = "http://www.apple.com";
		System.out.println("Connecting to page: " + urlString);
		try {
			URL url = new URL(urlString);
			String page = WebConnector.getWebpage(url);
			System.out.println(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
