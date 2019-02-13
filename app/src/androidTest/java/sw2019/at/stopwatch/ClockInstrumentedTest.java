package sw2019.at.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ClockInstrumentedTest {

	@Mock
	public Clock.TickListener tickListener;

	private Clock clock;

	private Locale locale;

	@Before
	public void setUp() {
		Context targetContext = InstrumentationRegistry.getTargetContext();
		locale = Locale.US;
		Looper mainLooper = targetContext.getMainLooper();

		clock = new Clock(new Handler(mainLooper), tickListener);
	}

	@Test
	public void testInitalState() {
		assertNotNull(clock);
		assertFalse(clock.isRunning());
		assertEquals(0, clock.getElapsedTime());
		assertEquals("0:00:00", clock.getElapsedTimeString(locale));

		verifyZeroInteractions(tickListener);
	}

	@Test
	public void testStartSetsRunning() {
		clock.start();

		assertTrue(clock.isRunning());
	}

	@Test
	public void testStartElapsesTime() throws InterruptedException {
		clock.start();

		long firstTimeStamp = clock.getElapsedTime();
		Thread.sleep(100);
		long secondTimeStamp = clock.getElapsedTime();

		assertThat(secondTimeStamp, is(greaterThan(firstTimeStamp)));
	}

	@Test
	public void testPauseStopsTimer() throws InterruptedException {
		clock.start();
		Thread.sleep(100);
		clock.pause();

		long firstTimeStamp = clock.getElapsedTime();
		Thread.sleep(100);
		long secondTimeStamp = clock.getElapsedTime();

		assertEquals(firstTimeStamp, secondTimeStamp);
		assertNotEquals(0, firstTimeStamp);
	}

	@Test
	public void testPauseStopsClock() {
		clock.start();
		clock.pause();

		assertFalse(clock.isRunning());
	}

	@Test
	public void testResetResetsTimer() {
		clock.start();
		clock.reset();

		long firstTimeStamp = clock.getElapsedTime();
		long secondTimeStamp = clock.getElapsedTime();

		assertEquals(firstTimeStamp, secondTimeStamp);
		assertEquals(0, firstTimeStamp);
	}

	@Test
	public void testResetStopsClock() {
		clock.start();
		clock.reset();

		assertFalse(clock.isRunning());
	}

	@Test
	public void testStartCallsTickListener() throws InterruptedException {
		clock.start();

		Thread.sleep(100);

		verify(tickListener, atLeastOnce()).onTick();
	}

	@Test
	public void testStringMatchesPattern() throws InterruptedException {
		clock.start();
		Thread.sleep(100);

		String elapsedTimeString = clock.getElapsedTimeString(locale);
		assertTrue(elapsedTimeString.matches("\\d:\\d\\d:\\d\\d"));
	}
}
