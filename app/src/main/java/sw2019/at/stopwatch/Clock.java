package sw2019.at.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;

import java.util.Locale;

public class Clock implements Runnable {
	private final Handler handler;
	private final TickListener tickListener;
	private boolean running;
	private long startTime;
	private long offsetTime;

	public Clock(Handler handler, TickListener tickListener) {
		this.handler = handler;
		this.tickListener = tickListener;
	}

	public void start() {
		running = true;
		startTime = SystemClock.elapsedRealtime() - offsetTime;
		handler.postDelayed(this, 50);
	}

	public boolean isRunning() {
		return running;
	}

	public long getElapsedTime() {
		return running ? SystemClock.elapsedRealtime() - startTime : offsetTime;
	}

	@Override
	public void run() {
		tickListener.onTick();
		handler.postDelayed(this, 50);
	}

	public void pause() {
		running = false;
		handler.removeCallbacks(this);
		offsetTime = SystemClock.elapsedRealtime() - startTime;
	}

	public void reset() {
		running = false;
		handler.removeCallbacks(this);
		offsetTime = 0;
	}

	public String getElapsedTimeString(Locale locale) {
		long elapsedTime = getElapsedTime();
		int milliseconds = (int) (elapsedTime % 1000) / 10;
		int seconds = (int) (elapsedTime / 1000) % 60;
		int minutes =  (int) (elapsedTime / 1000) / 60;
		return String.format(locale, "%01d", minutes)
				+ ":" + String.format(locale, "%02d", seconds)
				+ ":" + String.format(locale, "%02d", milliseconds);
	}

	public interface TickListener {
		void onTick();
	}
}
