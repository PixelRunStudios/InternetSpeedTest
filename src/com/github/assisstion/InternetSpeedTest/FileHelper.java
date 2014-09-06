package com.github.assisstion.InternetSpeedTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper{
	public static void write(File file, String output) throws IOException{
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(output);
		}
		finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	public static String read(File file) throws IOException{
		BufferedReader reader = null;
		try{
			String output = "";
			reader = new BufferedReader(new FileReader(file));
			String input = reader.readLine();
			while(input != null){
				output += input + "\n";
				input = reader.readLine();
			}
			return output;
		}
		finally{
			if(reader != null){
				reader.close();
			}
		}
	}
}
