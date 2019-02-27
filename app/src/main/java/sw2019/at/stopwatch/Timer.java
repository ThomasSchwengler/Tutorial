package sw2019.at.stopwatch;

import android.os.Handler;

import java.util.Locale;

public class Timer implements Runnable {
	private final Clock clock;
	private final Handler handler;
	private final TickListener tickListener;

	public Timer(Clock clock, Handler handler, TickListener tickListener) {
		this.clock = clock;
		this.handler = handler;
		this.tickListener = tickListener;
	}

	public void start() {
		clock.start();
		handler.postDelayed(this, 50);
	}

	public boolean isRunning() {
		return clock.isRunning();
	}

	public long getElapsedTime() {
		return clock.getElapsedTime();
	}

	@Override
	public void run() {
		tickListener.onTick();
		handler.postDelayed(this, 50);
	}

	public void pause() {
		clock.pause();
		handler.removeCallbacks(this);
	}

	public void reset() {
		clock.reset();
		handler.removeCallbacks(this);
	}

	public String getElapsedTimeString(Locale locale) {
		return clock.getElapsedTimeString(locale);
	}

	public interface TickListener {
		void onTick();
	}
}
