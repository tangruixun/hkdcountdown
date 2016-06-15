package com.trx.hkdcountdown;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    MySyncTimeTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        myTask = new MySyncTimeTask(this);
        myTask.execute();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (myTask.isCancelled()) {
            myTask = new MySyncTimeTask(this);
            myTask.execute();
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (!myTask.isCancelled()) {
            myTask.cancel(false);
        }
    }

    public void startTimer (Long untilMillSecond) {
        new CountDownTimer(untilMillSecond, 1000) {

            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                long years = days / 365;

                days = days - years * 365;
                hours = hours - days * 24 - years * 365 * 24;
                minutes = minutes - hours * 60 - days * 24 * 60 - years * 365 * 24 * 60;
                seconds = seconds - minutes * 60 - hours * 60 * 60 - days * 24 * 60 * 60 - years * 365 * 24 * 60 * 60;

                /*
                seconds = seconds - minutes * 60;
                minutes = minutes - hours * 60;
                hours = hours - days * 24;
                days = days - years * 365;
                */

                Log.i("--->", years+" years " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds ");

                setFlipBorder (years, days, hours, minutes, seconds);

                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //mTextField.setText("done!");
                Log.i("--->", "OnFinish ()");

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
            flip(R.id.yearUpRight, R.id.yearDownRight, y2, 'R');
            y2_current = y2;

            flip(R.id.yearUpLeft, R.id.yearDownLeft, y1, 'L');
            y1_current = y1;
        }

        if( d3 != d3_current){
            flip(R.id.dayUpRight, R.id.dayDownRight, d3, 'R');
            d3_current = d3;

            flip(R.id.dayUpMid, R.id.dayDownMid, d2, 'M');
            d2_current = d2;

            flip(R.id.dayUpLeft, R.id.dayDownLeft, d1, 'L');
            d1_current = d1;
        }

        if( h2 != h2_current){
            flip(R.id.hourUpRight, R.id.hourDownRight, h2, 'R');
            h2_current = h2;

            flip(R.id.hourUpLeft, R.id.hourDownLeft, h1, 'L');
            h1_current = h1;
        }

        if( m2 != m2_current){
            flip(R.id.minuteUpRight, R.id.minuteDownRight, m2, 'R');
            m2_current = m2;

            flip(R.id.minuteUpLeft, R.id.minuteDownLeft, m1, 'L');
            m1_current = m1;
        }

        if (s2 != s2_current){
            flip(R.id.secondUpRight, R.id.secondDownRight, s2, 'R');
            s2_current = s2;

            flip(R.id.secondUpLeft, R.id.secondDownLeft, s1, 'L');
            s1_current = s1;
        }
    }

    private void flip(int upperId, int lowerId, int s1, char p) {

        ImageView imgUpperView = (ImageView) findViewById (upperId);
        ImageView imgLowerView = (ImageView) findViewById(lowerId);

        String upperSrc = "_" + s1 + "_up_";
        String lowerSrc = "_" + s1 + "_down_";

        int imageUpperResource, imageLowerResource;
        Drawable upperDrawable, lowerDrawable;
        switch (p) {
            case 'L':
                upperSrc = "@drawable/" + upperSrc + "left";
                lowerSrc = "@drawable/" + lowerSrc + "left";
                break;
            case 'M':
                upperSrc = "@drawable/" + upperSrc + "mid";
                lowerSrc = "@drawable/" + lowerSrc + "mid";
                break;
            case 'R':
                upperSrc = "@drawable/" + upperSrc + "right";
                lowerSrc = "@drawable/" + lowerSrc + "right";
                break;
            default:
                upperSrc = "@drawable/_0_up_mid";
                lowerSrc = "@drawable/_0_down_mid";
                break;
        }

        imageUpperResource = getResources().getIdentifier(upperSrc, null, getPackageName());
        imageLowerResource = getResources().getIdentifier(lowerSrc, null, getPackageName());

        upperDrawable = ResourcesCompat.getDrawable(getResources(), imageUpperResource, null);
        lowerDrawable = ResourcesCompat.getDrawable(getResources(), imageLowerResource, null);

        imgUpperView.setImageDrawable(upperDrawable);
        imgLowerView.setImageDrawable(lowerDrawable);
    }

}
