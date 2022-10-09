package com.example.chesstimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView mTextViewCountDown;
    private TextView mTextViewCountDown2;
    private Button mButtonStartPause;
    private Button mButtonStartPause2;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;


    private boolean mTimerRunning;

    private boolean mTimerRunning2;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    private long mTimeLeftInMillis2 = START_TIME_IN_MILLIS;


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
        dialogPlayer();
        dialogTime();



        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                startTimer2();

            }
        });

        mButtonStartPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer2();
                startTimer();

            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                resetTimer();
                resetTimer2();
                mButtonStartPause.setText("Start");
                mButtonStartPause2.setText("Start");

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
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);

            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");


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
                mTimerRunning2 = false;
                mButtonStartPause2.setText("Start");
                mButtonStartPause2.setVisibility(View.INVISIBLE);


            }


        }.start();

        mTimerRunning2 = true;
        mButtonStartPause2.setText("pause");


    }

    private void pauseTimer2() {
        mCountDownTimer.cancel();
        mTimerRunning2 = false;
        mButtonStartPause2.setText("Start");


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
//set icon
                .setIcon(android.R.drawable.btn_star_big_on)
//set title
                .setTitle("Welcome")
//set message
                .setMessage("set the time")
//set positive button
                .setPositiveButton("5 min", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        mTimeLeftInMillis = 300000;
                        mTimeLeftInMillis2 = 300000;
                    }
                })
//set negative button

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
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Welcome")
//set message
                .setMessage("Which player is starting?")
//set positive button
                .setPositiveButton("Player one", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        startTimer();

                    }
                })
//set negative button
                .setNegativeButton("Player two", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        startTimer2();

                    }
                })
                .show();


    }


}