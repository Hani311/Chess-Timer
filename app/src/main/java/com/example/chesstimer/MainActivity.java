package com.example.chesstimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView mTextViewCountDown;
    private TextView mTextViewCountDown2;
    private ImageButton mButtonStartPause;
    private ImageButton mButtonStartPause2;
    private ImageButton mButtonReset;
    private CountDownTimer mCountDownTimer;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeLeftInMillis2 = START_TIME_IN_MILLIS;
    private SoundPool soundPool;
    private int end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mTextViewCountDown2 = findViewById(R.id.text_view_countdown2);

        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonStartPause2 = findViewById(R.id.button_start_pause2);
        mButtonReset = findViewById(R.id.button_reset);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        end = soundPool.load(this, R.raw.end, 1);

        dialogPlayer();
        dialogTime();

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                startTimer2();
                mButtonStartPause.setImageResource(R.drawable.red);
                mButtonStartPause2.setImageResource(R.drawable.green);
            }
        });

        mButtonStartPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer2();
                startTimer();
                mButtonStartPause2.setImageResource(R.drawable.red);
                mButtonStartPause.setImageResource(R.drawable.green);
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                resetTimer();
                resetTimer2();
                dialogPlayer();
                dialogTime();
            }
        });
        updateCountDownText();
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                soundPool.play(end, 1, 1, 0, 0, 1);

            }
        }.start();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateCountDownText2();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    // timer 2--------------------------------------------------------------------------------------------------------------


    private void startTimer2() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2 = millisUntilFinished;
                updateCountDownText2();
            }

            @Override
            public void onFinish() {
                soundPool.play(end, 1, 1, 0, 0, 1);
            }

        }.start();
    }

    private void pauseTimer2() {
        mCountDownTimer.cancel();

    }

    private void resetTimer2() {
        mTimeLeftInMillis2 = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateCountDownText2();

    }

    private void updateCountDownText2() {
        int minutes = (int) (mTimeLeftInMillis2 / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis2 / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown2.setText(timeLeftFormatted);
    }


    public void dialogTime() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star_big_on)
                .setTitle("Welcome")
                .setMessage("Set the time")
                .setPositiveButton("20 min", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        mTimeLeftInMillis = 1200000;
                        mTimeLeftInMillis2 = 1200000;
                    }
                })
                .setNegativeButton("10 Min", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        mTimeLeftInMillis = 600000;
                        mTimeLeftInMillis2 = 600000;

                    }
                })
                .show();
    }

    public void dialogPlayer() {
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Welcome")
                .setMessage("Who is starting?")
                .setPositiveButton("Player two ⇈ ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        startTimer();
                        mButtonStartPause2.setImageResource(R.drawable.red);
                        mButtonStartPause.setImageResource(R.drawable.green);
                    }
                })
                .setNegativeButton("Player one ⇊", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        startTimer2();
                        mButtonStartPause.setImageResource(R.drawable.red);
                        mButtonStartPause2.setImageResource(R.drawable.green);
                    }
                })
                .show();
    }

}