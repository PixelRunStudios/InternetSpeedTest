package com.github.assisstion.InternetSpeedTest.scheduler;

public class RepetitionScheduler implements Runnable{

	protected long currentIterations = 0;

	protected long iterations;
	protected long delay;
	protected Runnable runnable;
	protected LifeLine line;

	public static void main(String[] args){
		new Thread(new RepetitionScheduler(-1, 1000, new WebTimedProcess(),
				null)).start();
	}

	public RepetitionScheduler(long iterations, long delay, Runnable runnable,
			LifeLine line){
		this.delay = delay;
		this.runnable = runnable;
		this.iterations = iterations;
		this.line = line;
	}

	@Override
	public void run(){
		currentIterations = 0;
		while(iterations < 0 || currentIterations++ < iterations){
			if(line != null && !line.isAlive()){
				return;
			}
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
