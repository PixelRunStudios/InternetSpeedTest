package com.github.assisstion.InternetSpeedTest.scheduler;

import com.github.assisstion.InternetSpeedTest.WebProcessor;

public class WebTimedProcess implements Runnable{

	protected WebProcessor processor;

	protected long totalTime = 0;
	protected long totalBytes = 0;

	public WebTimedProcess(){
		processor = new WebProcessor();
		processor.silent = true;
	}

	@Override
	public void run(){
		System.out.println("Starting timed process at: " + System.currentTimeMillis());
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
		System.out.println("CumulativeAverage Speed (KB/s): " + (double) totalBytes / (double) totalTime);
		System.out.println();


	}

}
