package es.montanus.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {

    private static final String SECONDS = "seconds";
    private static final String WAS_RUNNING = "wasRunning";
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt(SECONDS);
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING);
        }
        runTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SECONDS, seconds);
        savedInstanceState.putBoolean(WAS_RUNNING, running);
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeView.setText(format());
                if (running)
                    seconds++;
                handler.postDelayed(this, 1000);
            }

            private String format() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                return String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
            }
        });
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }
}
