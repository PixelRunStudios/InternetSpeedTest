package com.github.assisstion.InternetSpeedTest.tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
		String name = "";
		String url = "";
		boolean thing = false;
		ArrayList<String> nameA = new ArrayList<String>();
		ArrayList<String> urlA = new ArrayList<String>();
		String[] sitesTemp = new String[100];
		String html = WebConnector.getWebpage(new URL(
				"http://en.wikipedia.org/wiki/List_of_most_popular_websites"));
		String[] split = html.split("</tr>\n<tr>");
		//Accidentally skipped 100th place
		for(int i = 0; i < split.length; i++){
			//System.out.println(i + "\n");
			thing = false;
			String website = split[i];
			if(i == 0){
				continue;
			}
			if(i == split.length - 1){
				continue;
			}
			String[] lines = website.split("\n");
			
			for(int j = 0; j < lines.length; j++){
				//System.out.println(lines[j]);
				switch(j){
					case 0:
						continue;
					case 1:
						if(lines[1].contains("\">")){
							String[] firstLine = lines[1].split("\">")[1].split("</a>");
							name = firstLine[0];
							nameA.add(name);
							thing = true;
						}
						break;
					case 2:
						if(thing){
							String[] secondLine = lines[2].split("<td>")[1].split("</td>");
							url = "http://www." + secondLine[0];
							urlA.add(url);
							//System.out.println(url);
						}
						break;
				}
			}
		}
		for(int i = 0; i < nameA.size();i++){
			System.out.println(i);
			System.out.println(nameA.get(i));
			System.out.println(urlA.get(i));
			System.out.println();
			
		}



		return sitesTemp;
	}
}
