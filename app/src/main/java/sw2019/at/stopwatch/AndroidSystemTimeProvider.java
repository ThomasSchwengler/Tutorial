package sw2019.at.stopwatch;

import android.os.SystemClock;

public class AndroidSystemTimeProvider implements Clock.SystemTimeProvider {
	@Override
	public long elapsedRealtime() {
		return SystemClock.elapsedRealtime();
	}
}
