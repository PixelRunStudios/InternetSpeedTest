package com.github.assisstion.InternetSpeedTest.scheduler;

import java.io.File;
import java.io.IOException;

import com.github.assisstion.InternetSpeedTest.FileHelper;
import com.github.assisstion.InternetSpeedTest.WebProcessor;

public class WebTimedProcess implements Runnable{

	protected WebProcessor processor;

	protected long totalTime = 0;
	protected long totalBytes = 0;

	private File file = new File("data/output2.txt");

	public WebTimedProcess(){
		processor = new WebProcessor();
		processor.silent = true;
		try{
			file.createNewFile();
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		String in = "";
		try{
			in = FileHelper.read(file);
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long startTime = System.currentTimeMillis();
		System.out.println("Starting timed process at: " + startTime);
		processor.process();
		long time = processor.getTotalTime();
		long bytes = processor.getTotalBytes();
		System.out.println("Total Bytes: " + bytes);
		System.out.println("Total Time (ms): " + time);
		System.out.println("Average Speed (KB/s): " + (double) bytes / (double) time);
		System.out.println();
		totalTime += time;
		totalBytes += bytes;

		System.out.println("Cumulative Bytes: " + totalBytes);
		System.out.println("Cumulative Time (ms): " + totalTime);

		double speed = (double) totalBytes / (double) totalTime;
		System.out.println("Cumulative Average Speed (KB/s): " + speed);
		System.out.println();

		in += startTime + "\t" + bytes + "\t" + time + "\t" + speed + "\n";

		try{
			FileHelper.write(file, in);
		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
