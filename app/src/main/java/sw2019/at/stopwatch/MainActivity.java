package sw2019.at.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Clock.TickListener {
	private Button startButton;
	private Clock clock;
	private TextView timeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startButton = findViewById(R.id.bt_start);
		Button resetButton = findViewById(R.id.bt_reset);
		Button lapButton = findViewById(R.id.bt_lap);
		timeTextView = findViewById(R.id.tv_time);

		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (clock.isRunning()) {
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

		clock = new Clock(new Handler(), this);
		updateTimeTextView();
	}

	public void updateTimeTextView() {
		Locale locale = getResources().getConfiguration().locale;
		timeTextView.setText(clock.getElapsedTimeString(locale));
	}

	public void onStartClicked() {
		startButton.setText(R.string.button_pause_text);
		clock.start();
		updateTimeTextView();
	}

	public void onPauseClicked() {
		startButton.setText(R.string.button_start_text);
		clock.pause();
		updateTimeTextView();
	}

	public void onResetClicked() {
		startButton.setText(R.string.button_start_text);
		clock.reset();
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
