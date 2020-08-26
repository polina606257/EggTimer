package com.example.eggtimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;

    public void resetTimer() {
        timerTextView.setText("0:00");
        timerSeekBar.setProgress(0);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go");
        counterIsActive = false;
    }

    public void buttonClicked(View view) {
        if(counterIsActive) {
          resetTimer();
        }
        else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("Stop");

            countDownTimer = new CountDownTimer
                    (timerSeekBar.getProgress() * 1000, 1000 - 500) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("done", "done");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondsString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondsString = "0" + secondsString;
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timeSeekBar);
        timerTextView = findViewById(R.id.countdownTimeView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    }
