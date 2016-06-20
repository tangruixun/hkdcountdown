package com.trx.hkdcountdown;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    MySyncTimeTask myTask;
    private AdView adView;
    private TextView titleDesView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myTask = new MySyncTimeTask(this);
        myTask.execute();

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow ().addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        if (getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().setBackgroundDrawableResource(R.drawable.warningp);
        } else {
            getWindow().setBackgroundDrawableResource(R.drawable.warningl);
        }

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/fg.ttc");
        titleDesView = (TextView) findViewById(R.id.title_des);
        assert titleDesView != null;
        titleDesView.setTypeface(customFont);

        adView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        ImageView dot1V = (ImageView) findViewById (R.id.dot1);
        ImageView dot2V = (ImageView) findViewById (R.id.dot2);
        ImageView dot3V = (ImageView) findViewById (R.id.dot3);
        ImageView dot4V = (ImageView) findViewById (R.id.dot4);
        final Animation blinkAnimation = AnimationUtils.loadAnimation (getApplicationContext (), R.anim.blink);
        assert dot1V != null;
        dot1V.setAnimation(blinkAnimation);
        assert dot2V != null;
        dot2V.setAnimation(blinkAnimation);
        assert dot3V != null;
        dot3V.setAnimation(blinkAnimation);
        assert dot4V != null;
        dot4V.setAnimation(blinkAnimation);

        ImageView y1uV, d1uV, h1uV, m1uV, s1uV;
        ImageView y1dV, d1dV, h1dV, m1dV, s1dV;
        ImageView y2uV, d3uV, h2uV, m2uV, s2uV;
        ImageView y2dV, d3dV, h2dV, m2dV, s2dV;
        ImageView d2uV,d2dV;

        y1uV = (ImageView) findViewById (R.id.yearUpLeft);
        y1dV = (ImageView) findViewById (R.id.yearDownLeft);
        y2uV = (ImageView) findViewById (R.id.yearUpRight);
        y2dV = (ImageView) findViewById (R.id.yearDownRight);
        d1uV = (ImageView) findViewById (R.id.dayUpLeft);
        d1dV = (ImageView) findViewById (R.id.dayDownLeft);
        d2uV = (ImageView) findViewById (R.id.dayUpMid);
        d2dV = (ImageView) findViewById (R.id.dayDownMid);
        d3uV = (ImageView) findViewById (R.id.dayUpRight);
        d3dV = (ImageView) findViewById (R.id.dayDownRight);
        h1uV = (ImageView) findViewById (R.id.hourUpLeft);
        h1dV = (ImageView) findViewById (R.id.hourDownLeft);
        h2uV = (ImageView) findViewById (R.id.hourUpRight);
        h2dV = (ImageView) findViewById (R.id.hourDownRight);
        m1uV = (ImageView) findViewById (R.id.minuteUpLeft);
        m1dV = (ImageView) findViewById (R.id.minuteDownLeft);
        m2uV = (ImageView) findViewById (R.id.minuteUpRight);
        m2dV = (ImageView) findViewById (R.id.minuteDownRight);
        s1uV = (ImageView) findViewById (R.id.secondUpLeft);
        s1dV = (ImageView) findViewById (R.id.secondDownLeft);
        s2uV = (ImageView) findViewById (R.id.secondUpRight);
        s2dV = (ImageView) findViewById (R.id.secondDownRight);

        ImageView [] leftUpperViews = {y1uV, d1uV, h1uV, m1uV, s1uV};
        for (ImageView view :
                leftUpperViews) {
            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch (View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    Random randomGenerator = new Random ();
                    int index = randomGenerator.nextInt (10);
                    String srcString = "@drawable/_" + index + "_up_left";

                    int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                    Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                    view.setImageDrawable (imgDrawable);
                    resetUITimer ();
                    playSound (R.raw.feed);
                    return false;
                }
            });
        }

        ImageView [] leftLowerViews = {y1dV, d1dV, h1dV, m1dV, s1dV};
        for (ImageView view :
                leftLowerViews) {
            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch (View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    Random randomGenerator = new Random ();
                    int index = randomGenerator.nextInt (10);
                    String srcString = "@drawable/_" + index + "_down_left";

                    int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                    Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                    view.setImageDrawable (imgDrawable);
                    resetUITimer ();
                    playSound (R.raw.feed);
                    return false;
                }
            });
        }

        ImageView [] rightUpperViews = {y2uV, d3uV, h2uV, m2uV, s2uV};
        for (ImageView view :
                rightUpperViews) {
            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch (View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    Random randomGenerator = new Random ();
                    int index = randomGenerator.nextInt (10);
                    String srcString = "@drawable/_" + index + "_up_right";

                    int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                    Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                    view.setImageDrawable (imgDrawable);
                    resetUITimer ();
                    playSound (R.raw.feed);
                    return false;
                }
            });
        }

        ImageView [] rightLowerViews = {y2dV, d3dV, h2dV, m2dV, s2dV};
        for (ImageView view :
                rightLowerViews) {
            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch (View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    Random randomGenerator = new Random ();
                    int index = randomGenerator.nextInt (10);
                    String srcString = "@drawable/_" + index + "_down_right";

                    int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                    Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                    view.setImageDrawable (imgDrawable);
                    resetUITimer ();
                    playSound (R.raw.feed);
                    return false;
                }
            });
        }

        assert d2uV != null;
        d2uV.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                Random randomGenerator = new Random ();
                int index = randomGenerator.nextInt (10);
                String srcString = "@drawable/_" + index + "_up_mid";

                int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                view.setImageDrawable (imgDrawable);
                resetUITimer ();
                playSound (R.raw.feed);
                return false;
            }
        });

        assert d2dV != null;
        d2dV.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                Random randomGenerator = new Random ();
                int index = randomGenerator.nextInt (10);
                String srcString = "@drawable/_" + index + "_down_mid";

                int imgResource = getResources().getIdentifier(srcString, null, getPackageName());
                Drawable imgDrawable = ResourcesCompat.getDrawable(getResources(), imgResource, null);

                view.setImageDrawable (imgDrawable);
                resetUITimer ();
                playSound (R.raw.feed);
                return false;
            }
        });
    }

    private void playSound (int res) {
        MediaPlayer mp = MediaPlayer.create (this, res);
        mp.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
            @Override
            public void onCompletion (MediaPlayer mp) {
                mp.reset ();
                mp.release ();
            }
        });
        mp.start ();
    }

    private void resetUITimer () {
        y1_current = -1;
        y2_current = -1;
        d1_current = -1;
        d2_current = -1;
        d3_current = -1;
        h1_current = -1;
        h2_current = -1;
        m1_current = -1;
        m2_current = -1;
        s1_current = -1;
        s2_current = -1;
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
        if (myTask.isCancelled()) {
            myTask = new MySyncTimeTask(this);
            myTask.execute();
        }
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
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
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().setBackgroundDrawableResource(R.drawable.warningp);
        } else {
            getWindow().setBackgroundDrawableResource(R.drawable.warningl);
        }
        super.onConfigurationChanged (newConfig);
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
                titleDesView.setText (R.string.hkdied);
                playSound (R.raw.eas);
            }
        }.start();
    }

    private void setFlipBorder(long years, long days, long hours, long minutes, long seconds) {

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

        if( y2 != y2_current) {
            flip (R.id.yearUpRight, R.id.yearDownRight, y2, 'R');
            y2_current = y2;
        }

        if( y1 != y1_current) {
            flip(R.id.yearUpLeft, R.id.yearDownLeft, y1, 'L');
            y1_current = y1;
        }

        if( d3 != d3_current) {
            flip (R.id.dayUpRight, R.id.dayDownRight, d3, 'R');
            d3_current = d3;
        }

        if( d2 != d2_current) {
            flip (R.id.dayUpMid, R.id.dayDownMid, d2, 'M');
            d2_current = d2;
        }

        if( d1 != d1_current) {
            flip(R.id.dayUpLeft, R.id.dayDownLeft, d1, 'L');
            d1_current = d1;
        }

        if( h2 != h2_current) {
            flip (R.id.hourUpRight, R.id.hourDownRight, h2, 'R');
            h2_current = h2;
        }

        if( h1 != h1_current) {
            flip(R.id.hourUpLeft, R.id.hourDownLeft, h1, 'L');
            h1_current = h1;
        }

        if( m2 != m2_current) {
            flip (R.id.minuteUpRight, R.id.minuteDownRight, m2, 'R');
            m2_current = m2;
        }

        if( m1 != m1_current) {
            flip(R.id.minuteUpLeft, R.id.minuteDownLeft, m1, 'L');
            m1_current = m1;
        }

        if (s2 != s2_current) {
            flip (R.id.secondUpRight, R.id.secondDownRight, s2, 'R');
            s2_current = s2;
        }

        if (s1 != s1_current) {
            flip(R.id.secondUpLeft, R.id.secondDownLeft, s1, 'L');
            s1_current = s1;
        }
    }

    private void flip(int upperId, int lowerId, int s1, char p) {

        ImageView imgUpperView = (ImageView) findViewById (upperId);
        ImageView imgLowerView = (ImageView) findViewById (lowerId);

        Animation slideReverseBackAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_reverse_back);
        Animation slideBackAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_back);
        assert imgUpperView != null;
        imgUpperView.setAnimation(slideReverseBackAnimation);
        assert imgLowerView != null;
        imgLowerView.setAnimation(slideBackAnimation);

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

        Animation slideReverseAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_reverse);
        Animation slideAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);

        imgUpperView.setImageDrawable(upperDrawable);
        imgUpperView.setAnimation(slideReverseAnimation);
        imgLowerView.setImageDrawable(lowerDrawable);
        imgLowerView.setAnimation(slideAnimation);
    }

}
