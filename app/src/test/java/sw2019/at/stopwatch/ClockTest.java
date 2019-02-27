package sw2019.at.stopwatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ClockTest {

	@Mock
	public Clock.SystemTimeProvider timeProvider;

	private Clock clock;

	private Locale locale = Locale.US;

	@Before
	public void setUp() {
		clock = new Clock(timeProvider);
	}

	@Test
	public void testInitialState() {
		assertNotNull(clock);
		assertFalse(clock.isRunning());
		assertEquals(0, clock.getElapsedTime());
		assertEquals("0:00:00", clock.getElapsedTimeString(locale));
	}

	@Test
	public void testStartSetsRunning() {
		clock.start();

		assertTrue(clock.isRunning());
	}

	@Test
	public void testStartElapsesTime() {
		when(timeProvider.elapsedRealtime()).thenReturn(0L, 5L, 10L, 15L);

		clock.start();

		long firstTimeStamp = clock.getElapsedTime();
		long secondTimeStamp = clock.getElapsedTime();

		assertThat(secondTimeStamp, is(greaterThan(firstTimeStamp)));
	}

	@Test
	public void testPauseStopsTimer() {
		when(timeProvider.elapsedRealtime()).thenReturn(0L, 5L, 10L, 15L);

		clock.start();
		clock.pause();

		long firstTimeStamp = clock.getElapsedTime();
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
		when(timeProvider.elapsedRealtime()).thenReturn(0L, 5L, 10L, 15L);

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
	public void testStringMatchesPattern() {
		when(timeProvider.elapsedRealtime()).thenReturn(0L, 5L, 10L, 15L);

		clock.start();

		String elapsedTimeString = clock.getElapsedTimeString(locale);
		assertTrue(elapsedTimeString.matches("\\d:\\d\\d:\\d\\d"));
	}
}
