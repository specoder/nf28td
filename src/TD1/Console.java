package TD1;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Console extends Observable {
	
	static long maxTime = 1000; // 1 second
	
	private Timer timer;
	private long interval;
	private int index;
	
	public Console(){
		timer = new Timer();
		interval = maxTime*ConsoleView.sliderInitPercent/100;
		index = 0;
	}
	
	public void setInterval(long intvl){
		interval = intvl;
	}
	
	public long getInterval(){
		return interval;
	}
	
	public void time(){
		index ++;
		ImageIcon icon = new ImageIcon("image"+index%8+".jpg","moom");
		System.out.println("timer running");
		notify(icon);
	}

	public void notify(ImageIcon image) {
		setChanged();
		notifyObservers(image);
	}
 
	public Timer getTimer(){
		return timer;  
	}
	
	public void createTimer(){
		timer = new Timer();  
	}
	
	public void startTimer(){
		ImageTimerTask task = new ImageTimerTask(this);
		System.out.println("interval = "+interval);
		timer.schedule(task,500,interval);
	}
	
	public void stopTimer(){
		timer.cancel();
		createTimer();
	}
}

class ImageTimerTask extends TimerTask{
	
	private Console c;
	
	public ImageTimerTask (Console consl){
		c = consl;
	}
	
	public void run(){
		c.time();
	}
} 