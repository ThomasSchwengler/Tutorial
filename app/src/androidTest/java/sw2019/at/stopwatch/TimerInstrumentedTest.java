package sw2019.at.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimerInstrumentedTest {

	@Mock
	public Clock clock;

	@Mock
	Timer.TickListener tickListener;

	Handler handler;

	Timer timer;

	@Before
	public void setUp() {
		Context context = InstrumentationRegistry.getTargetContext();

		handler = new Handler(context.getMainLooper());
		timer = new Timer(clock, handler, tickListener);
	}

	@Test
	public void testInitalState() {
		assertNotNull(timer);
		assertFalse(timer.isRunning());
		assertEquals(0, timer.getElapsedTime());
	}

	@Test
	public void testStartElapsesTime() {
		when(clock.getElapsedTime()).thenReturn(0L, 10L, 20L);
		timer.start();

		long firstTimeStamp = timer.getElapsedTime();
		long secondTimeStamp = timer.getElapsedTime();

		assertThat(secondTimeStamp, is(greaterThan(firstTimeStamp)));
	}

	@Test
	public void testStartCallsTickListener() throws InterruptedException {
		timer.start();

		Thread.sleep(100);

		verify(tickListener, atLeastOnce()).onTick();
	}
}
