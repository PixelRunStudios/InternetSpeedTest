package com.github.assisstion.InternetSpeedTest.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import com.github.assisstion.InternetSpeedTest.MainGUI;
import com.github.assisstion.InternetSpeedTest.helper.MathHelper;
import com.github.assisstion.Shared.Pair;

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
	 * @param silent if printing is allowed
	 * @return a long array with two values: the number of bytes of the website and the ms passsed
	 * @throws IOException when the webpage cannot be connected to
	 */
	public static Pair<Long, Long> webpageByteCount(URL url, boolean silent, MainGUI gui,
			Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders, int attemptCount) throws IOException{
		WebpageByteCount wbc = new WebpageByteCount(url, silent, gui, senders, attemptCount);
		wbc.execute();
		try{
			return wbc.get();
		}
		catch(Exception e){
			throw new IOException(e);
		}
	}

	public static class WebpageByteCount extends SwingWorker<Pair<Long, Long>, Pair<Long, Long>>{

		protected int attemptCount;
		protected URL url;
		protected boolean silent;
		protected MainGUI gui;
		protected Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders;

		private long count;
		private long startTime;

		public WebpageByteCount(URL url, boolean silent, MainGUI gui,
				Collection<InfoSender<Pair<Pair<Long, Long>, Integer>>> senders, int attemptCount){
			this.url = url;
			this.silent = silent;
			this.gui = gui;
			this.senders = senders;
			this.attemptCount = attemptCount;
		}

		@Override
		protected Pair<Long, Long> doInBackground() throws Exception{
			//Gets the input stream to load the website: timeout 5 seconds
			InputStream is = loadWithTimeOut(url, 5000);
			//Wraps the input stream with a buffer
			BufferedInputStream bis = new BufferedInputStream(is);
			//Starts the timer
			startTime = System.currentTimeMillis();
			//Counts one unicode byte: timeout 5 seconds
			//int value = bis.read();
			int value = readWithTimeOut(bis, 5000);
			count = 0;
			while(value >= 0){
				count++;
				if(count % 1000 == 0){
					publish(new Pair<Long, Long>(count, System.currentTimeMillis()));
				}
				if(count % 1000000 == 0){
					value = readWithTimeOut(bis, 5000);
					if(!silent){
						System.out.println("Counted " + count / 1000000 + " million.");
					}
					if(count >= 100000000){
						break;
					}
				}
				else{
					//Sets the value to the next character
					value = bis.read();
					//value = readWithTimeOut(bis, 1000);
				}
			}
			long difference = System.currentTimeMillis() - startTime;
			return new Pair<Long, Long>(count, difference);
		}

		@Override
		protected void process(List<Pair<Long, Long>> pairs){
			if(gui == null){
				return;
			}
			Pair<Long, Long> pair = pairs.get(pairs.size() - 1);
			long bytes = pair.getValueOne();
			long time = pair.getValueTwo() - startTime;
			gui.siteKB.setText(String.valueOf(bytes / 1000));
			gui.siteTime.setText(String.valueOf(time / 1000.0));
			gui.speed.setText(String.valueOf(MathHelper.roundThreeDecimals((double) bytes / (double) time)));
			for(InfoSender<Pair<Pair<Long, Long>, Integer>> sender : senders){
				sender.send(new Pair<Pair<Long, Long>, Integer>(new Pair<Long, Long>(bytes, time), attemptCount));
			}

		}

	}

	/**
	 * Read a byte of data from the input stream with a certain time limit
	 * @param is
	 * @param timeOut
	 * @return
	 */
	public static int readWithTimeOut(InputStream is, long timeOut) throws IOException{
		TimedReader reader = new TimedReader(is);
		TimeOutRunner<Integer> runner = new TimeOutRunner<Integer>(reader, timeOut);
		reader.setRunner(runner);
		return runner.get();
	}

	public static InputStream loadWithTimeOut(URL url, long timeOut) throws IOException{
		TimedURLLoader reader = new TimedURLLoader(url);
		TimeOutRunner<InputStream> runner = new TimeOutRunner<InputStream>(reader, timeOut);
		reader.setRunner(runner);
		return runner.get();
	}

	public static class TimeOutRunner<T>{
		protected T value;
		protected Runnable runnable;
		protected boolean runnableStarted;

		protected long timeOut;

		protected Exception toBeThrown;

		public TimeOutRunner(Runnable runnable, long timeOut){
			this.runnable = runnable;
			this.timeOut = timeOut;
		}

		public T get() throws IOException{
			value = null;
			toBeThrown = null;
			runnableStarted = false;
			long startTime = System.currentTimeMillis();
			while(System.currentTimeMillis() - startTime < timeOut){
				synchronized(this){
					if(!runnableStarted){
						runnableStarted = true;
						new Thread(runnable).start();
					}
					try{
						wait(timeOut);
					}
					catch(InterruptedException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(toBeThrown != null){
						throw new IOException(
								"Exception caught while loading", toBeThrown);
					}
					if(value != null){
						return value;
					}
				}
			}
			System.out.println("TO!");
			throw new IOException("Loading timeout");
		}

		public void putValue(T value){
			this.value = value;
		}

		public void throwException(Exception e){
			toBeThrown = e;
			synchronized(this){
				notifyAll();
			}
		}
	}

	public static class TimedReader implements Runnable{

		protected TimeOutRunner<Integer> runner;

		protected InputStream is;

		public TimedReader(InputStream is){
			this.is = is;
		}

		public void setRunner(TimeOutRunner<Integer> runner){
			this.runner = runner;
		}

		@Override
		public void run(){
			try{
				runner.putValue(is.read());
			}
			catch(Exception e){
				runner.throwException(e);
			}
			synchronized(runner){
				runner.notifyAll();
			}
		}

	}

	public static class TimedURLLoader implements Runnable{

		protected URL url;

		protected TimeOutRunner<InputStream> runner;

		public TimedURLLoader(URL url){
			this.url = url;
		}

		public void setRunner(TimeOutRunner<InputStream> runner){
			this.runner = runner;
		}

		@Override
		public void run(){
			try{
				runner.putValue(url.openConnection().getInputStream());
			}
			catch(Exception e){
				runner.throwException(e);
			}
			synchronized(runner){
				runner.notifyAll();
			}
		}

	}

}
