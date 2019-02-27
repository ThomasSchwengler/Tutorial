package sw2019.at.stopwatch;

import android.os.Bundle;

import java.util.Locale;

public class Clock {
	private static final String START_TIME = "START_TIME";
	private static final String OFFSET_TIME = "OFFSET_TIME";
	private static final String RUNNING = "RUNNING";
	private final SystemTimeProvider timeProvider;
	private boolean running;
	private long startTime;
	private long offsetTime;

	public Clock(SystemTimeProvider timeProvider) {
		this.timeProvider = timeProvider;
	}

	public void start() {
		if (!running) {
			running = true;
			startTime = calculateElapsedTime(offsetTime);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public long getElapsedTime() {
		return running ? calculateElapsedTime(startTime) : offsetTime;
	}

	public void saveState(Bundle bundle) {
		bundle.putLong(START_TIME, startTime);
		bundle.putLong(OFFSET_TIME, offsetTime);
		bundle.putBoolean(RUNNING, running);
	}

	public void restoreState(Bundle bundle) {
		startTime = bundle.getLong(START_TIME);
		offsetTime = bundle.getLong(OFFSET_TIME);
		running = bundle.getBoolean(RUNNING);
	}

	private long calculateElapsedTime(long startTime) {
		return timeProvider.elapsedRealtime() - startTime;
	}

	public void pause() {
		running = false;
		offsetTime = calculateElapsedTime(startTime);
	}

	public void reset() {
		running = false;
		offsetTime = 0;
	}

	public String getElapsedTimeString(Locale locale) {
		long elapsedTime = getElapsedTime();
		int milliseconds = (int) (elapsedTime % 1000) / 10;
		int seconds = (int) (elapsedTime / 1000) % 60;
		int minutes = (int) (elapsedTime / 1000) / 60;
		return String.format(locale, "%01d", minutes)
				+ ":" + String.format(locale, "%02d", seconds)
				+ ":" + String.format(locale, "%02d", milliseconds);
	}

	public interface SystemTimeProvider {
		long elapsedRealtime();
	}
}
