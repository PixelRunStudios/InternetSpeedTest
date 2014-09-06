package com.github.assisstion.InternetSpeedTest.scheduler;

public class RepetitionScheduler implements Runnable{

	protected long currentIterations = 0;

	protected long iterations;
	protected long delay;
	protected Runnable runnable;

	public static void main(String[] args){
		new Thread(new RepetitionScheduler(10, 1000, new WebTimedProcess())).start();
	}

	public RepetitionScheduler(long iterations, long delay, Runnable runnable){
		this.delay = delay;
		this.runnable = runnable;
		this.iterations = iterations;
	}

	@Override
	public void run(){
		currentIterations = 0;
		while(currentIterations++ < iterations){
			runnable.run();
			try{
				Thread.sleep(delay);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
