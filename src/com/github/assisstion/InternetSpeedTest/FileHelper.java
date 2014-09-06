package com.github.assisstion.InternetSpeedTest;

import java.io.BufferedWriter;
import java.io.File;
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
}
