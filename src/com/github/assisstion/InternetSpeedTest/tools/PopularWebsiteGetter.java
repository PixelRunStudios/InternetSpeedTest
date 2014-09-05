package com.github.assisstion.InternetSpeedTest.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.github.assisstion.InternetSpeedTest.web.WebConnector;

//Use http://en.wikipedia.org/wiki/List_of_most_popular_websites
//Sept. 5, 2014
public class PopularWebsiteGetter{
	
	public static void main(String[] args){
		try{
			getWebsites();
		}
		catch(MalformedURLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] getWebsites() throws MalformedURLException, IOException{
		String[] sitesTemp = new String[100];
		String html = WebConnector.getWebpage(new URL(
				"http://en.wikipedia.org/wiki/List_of_most_popular_websites"));
		String[] split = html.split("</tr>\n<tr>");
		//Accidentally skipped 100th place
		for(int i = 0; i < split.length; i++){
			String website = split[i];
			if(i == 0){
				continue;
			}
			if(i == split.length - 1){
				continue;
			}
			String[] lines = website.split("\n");
			for(int j = 0; j < lines.length; j++){
				String line = lines[j];
				if(j == 0){
					continue;
				}
				System.out.println(line);
			}
		}
		return sitesTemp;
	}
}
