package com.github.assisstion.InternetSpeedTest.tools;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.github.assisstion.InternetSpeedTest.helper.FileHelper;
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

	public static String[] getWebsites() throws MalformedURLException,
			IOException{
		String name = "";
		boolean thing = false;
		String url = "";
		ArrayList<String> nameA = new ArrayList<String>();
		ArrayList<String> urlA = new ArrayList<String>();
		String[] sitesTemp = new String[100];
		String html = WebConnector.getWebpage(new URL(
				"http://en.wikipedia.org/wiki/List_of_most_popular_websites"));
		String[] split = html.split("</tr>\n<tr>");
		// Accidentally skipped 100th place
		for(int i = 0; i < split.length; i++){
			thing = false;
			// System.out.println(i + "\n");
			String website = split[i];
			if(i == 0){
				continue;
			}
			if(i == split.length - 1){
				website = split[i].split("</tr>")[0];
			}
			String[] lines = website.split("\n");
			String[] fifthLine = lines[5].split("<td>")[1].split("</td>");
			if(fifthLine[0].contains("Pornography")){
				thing = true;
			}
			if(!thing){
				for(int j = 0; j < lines.length; j++){
					// System.out.println(lines[j]);
					switch(j){
						case 0:
							continue;
						case 1:
							if(lines[1].contains("\">")){
								String[] firstLine = lines[1].split("\">")[1]
										.split("</a>");
								name = firstLine[0];
								nameA.add(name);
							}
							else{
								String[] firstLine = lines[1].split("<td>")[1]
										.split("</td>");
								name = firstLine[0];
								nameA.add(name);
							}
							break;
						case 2:
							String[] secondLine = lines[2].split("<td>")[1]
									.split("</td>");
							url = "http://www." + secondLine[0];
							urlA.add(url);
							// System.out.println(url);
							break;

					}
				}
			}
		}

		for(int i = 0; i < nameA.size(); i++){
			System.out.println(i);
			System.out.println(nameA.get(i));
			System.out.println(urlA.get(i));
			System.out.println();

		}

		String everything = "";

		for(int i = 0; i < nameA.size(); i++){
			everything += nameA.get(i) + "	" + urlA.get(i) + "\n";
		}

		File file = new File("");

		FileHelper.write(file, everything);

		return sitesTemp;
	}
}
