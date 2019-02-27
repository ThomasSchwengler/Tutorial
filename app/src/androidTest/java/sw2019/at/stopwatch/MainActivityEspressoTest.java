package sw2019.at.stopwatch;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

	@Rule
	public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void testButtonsVisible() {
		onView(withId(R.id.bt_reset)).check(matches(isDisplayed()));
		onView(withId(R.id.bt_start)).check(matches(isDisplayed()));
		onView(withId(R.id.bt_lap)).check(matches(isDisplayed()));
	}

	@Test
	public void testTimeViewVisible() {
		onView(withId(R.id.tv_time)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_time)).check(matches(withText("0:00:00")));
	}

	@Test
	public void testStartButtonChangesTimeView() {
		onView(withId(R.id.bt_start)).perform(click());
		onView(withId(R.id.tv_time)).check(matches(not(withText("0:00:00"))));
	}

	@Test
	public void testPauseButtonKeepsTimeView() {
		onView(withId(R.id.bt_start)).perform(click());
//		onView(withId(R.id.bt_start)).perform(click());

		TextView textView = mainActivityRule.getActivity().findViewById(R.id.tv_time);
		String currentTimeText = textView.getText().toString();

		onView(withId(R.id.tv_time)).check(matches(withText(currentTimeText)));
	}

	@Test
	public void testResetButtonResetsTimeView() {
		onView(withId(R.id.bt_start)).perform(click());
		onView(withId(R.id.bt_reset)).perform(click());
		onView(withId(R.id.tv_time)).check(matches(withText("0:00:00")));
	}
}
