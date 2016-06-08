package com.trx.hkdcountdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Long nowTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySyncTimeTask myTask = new MySyncTimeTask(this);
        myTask.execute();

    }

    public void startTimer (Long untilMillSecond) {

        int sec = Math.floor();untilMillSecond / 1000);





        new CountDownTimer(untilMillSecond, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                mTextField.setText("done!");
            }

        }.start();    }

}
