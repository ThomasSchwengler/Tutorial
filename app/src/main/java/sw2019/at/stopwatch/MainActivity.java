package sw2019.at.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Timer.TickListener {
	private Button startButton;
	private Timer timer;
	private TextView timeTextView;
	private Clock clock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		clock = new Clock(new AndroidSystemTimeProvider());
		timer = new Timer(clock, new Handler(), this);

		startButton = findViewById(R.id.bt_start);
		Button resetButton = findViewById(R.id.bt_reset);
		Button lapButton = findViewById(R.id.bt_lap);
		timeTextView = findViewById(R.id.tv_time);

		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (timer.isRunning()) {
					onPauseClicked();
				} else {
					onStartClicked();
				}
			}
		});
		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onResetClicked();
			}
		});
		lapButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onLapClicked();
			}
		});

		if (savedInstanceState != null) {
			clock.restoreState(savedInstanceState.getBundle("BUNDLE_CLOCK"));
			if (clock.isRunning()) {
				startButton.setText(R.string.button_pause_text);
				timer.start();
			}
		}

		updateTimeTextView();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		Bundle bundle = new Bundle();
		clock.saveState(bundle);
		outState.putBundle("BUNDLE_CLOCK", bundle);
	}

	public void updateTimeTextView() {
		Locale locale = getResources().getConfiguration().locale;
		timeTextView.setText(timer.getElapsedTimeString(locale));
	}

	public void onStartClicked() {
		startButton.setText(R.string.button_pause_text);
		timer.start();
		updateTimeTextView();
	}

	public void onPauseClicked() {
		startButton.setText(R.string.button_start_text);
		timer.pause();
		updateTimeTextView();
	}

	public void onResetClicked() {
		startButton.setText(R.string.button_start_text);
		timer.reset();
		updateTimeTextView();
	}

	public void onLapClicked() {
		// TODO
	}

	@Override
	public void onTick() {
		updateTimeTextView();
	}
}
