package com.trx.hkdcountdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySyncTimeTask myTask = new MySyncTimeTask(this);
        myTask.execute();

    }

    public void startTimer (Long untilMillSecond) {
        new CountDownTimer(untilMillSecond, 1000) {

            public void onTick(long millisUntilFinished) {


                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                long years = days / 365;

                seconds = seconds - minutes * 60;
                minutes = minutes - hours * 60;
                hours = hours - days * 24;
                days = days - years * 365;

                Log.i("--->", years+" years " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds ");

                setFlipBorder (years, days, hours, minutes, seconds);

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //mTextField.setText("done!");
            }

        }.start();
    }

    private void setFlipBorder(long years, long days, long hours, long minutes, long seconds) {
        int y1_current = -1;
        int y2_current = -1;
        int d1_current = -1;
        int d2_current = -1;
        int d3_current = -1;
        int h1_current = -1;
        int h2_current = -1;
        int m1_current = -1;
        int m2_current = -1;
        int s1_current = -1;
        int s2_current = -1;

        int y1 = (int) years / 10;
        int y2 = (int) years % 10;
        int d1 = (int) days / 100;
        int d2 = (int) days / 10;
        int d3 = (int) days % 10;
        int h1 = (int) hours / 10;
        int h2 = (int) hours % 10;
        int m1 = (int) minutes / 10;
        int m2 = (int) minutes % 10;
        int s1 = (int) seconds / 10;
        int s2 = (int) seconds % 10;

        if( y2 != y2_current){
            flip(R.id.yearUpRight, R.id.yearDownRight, y2, 'Double/Up/Right/', 'Double/Down/Right/');
            y2_current = y2;

            flip('yearsUpLeft', 'yearsDownLeft', y1, 'Double/Up/Left/', 'Double/Down/Left/');
            y1_current = y1;
        }

        if( d3 != d3_current){
            flip('daysUpRight', 'daysDownRight', d3, 'Double/Up/Right/', 'Double/Down/Right/');
            d3_current = d3;

            flip('daysUpMid', 'daysDownMid', d2, 'Double/Up/Mid/', 'Double/Down/Mid/');
            d2_current = d2;

            flip('daysUpLeft', 'daysDownLeft', d1, 'Double/Up/Left/', 'Double/Down/Left/');
            d1_current = d1;
        }

        if( h2 != h2_current){
            flip('hoursUpRight', 'hoursDownRight', h2, 'Double/Up/Right/', 'Double/Down/Right/');
            h2_current = h2;

            flip('hoursUpLeft', 'hoursDownLeft', h1, 'Double/Up/Left/', 'Double/Down/Left/');
            h1_current = h1;
        }

        if( m2 != m2_current){
            flip('minutesUpRight', 'minutesDownRight', m2, 'Double/Up/Right/', 'Double/Down/Right/');
            m2_current = m2;

            flip('minutesUpLeft', 'minutesDownLeft', m1, 'Double/Up/Left/', 'Double/Down/Left/');
            m1_current = m1;
        }

        if (s2 != s2_current){
            flip('secondsUpRight', 'secondsDownRight', s2, 'Double/Up/Right/', 'Double/Down/Right/');
            s2_current = s2;

            flip('secondsUpLeft', 'secondsDownLeft', s1, 'Double/Up/Left/', 'Double/Down/Left/');
            s1_current = s1;
        }

    }

}
