package com.trx.hkdcountdown;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TRX on 06/08/2016.
 */
public class MySyncTimeTask extends AsyncTask <Integer, Void, Long> {

    private WeakReference <MainActivity> weakRef;
    private final static String DEADLINE = "2047-07-01T00:00:00.000+0800";
    //final static String DEADLINE = "Jun 13 14:30:00 GMT+08:30 2016";

    MySyncTimeTask(MainActivity activity) {
        weakRef = new WeakReference<>(activity);
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param flags The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    // flags: 0 - first run
    //        1 - check online;

    @Override
    protected Long doInBackground(Integer... flags) {
        //long now = SystemClock.;
        long milliSecondsUntilHKDir;
        long hkDeadTimeinMillSecond = ConvertDateToMillSec(DEADLINE);
        long now = 0;
        long needToChange = 0;
        SntpClient sntpClient = new SntpClient();

        if (flags [0] == 0) {
            milliSecondsUntilHKDir = hkDeadTimeinMillSecond - System.currentTimeMillis();

        } else {
            if (sntpClient.requestTime("time.hko.hk", 2000)) {
                now = sntpClient.getNtpTime() + SystemClock.elapsedRealtime() - sntpClient.getNtpTimeReference();
                long localnow = System.currentTimeMillis();
                if (Math.abs(localnow-now) > 1000*60*60) {
                    needToChange = 1;
                }
            }

            if (needToChange == 1) {
                // local system is wrong
                milliSecondsUntilHKDir = hkDeadTimeinMillSecond - now;
            } else {
                milliSecondsUntilHKDir = hkDeadTimeinMillSecond - System.currentTimeMillis();
            }
        }

        //Date time = new Date(now);
        Log.i ("--->", milliSecondsUntilHKDir + " HKDIETIME ");
        if (milliSecondsUntilHKDir > 0) {
            return milliSecondsUntilHKDir;
        } else {
            return 0L;
        }
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param aLong The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        MainActivity mainActivity = weakRef.get();
        mainActivity.startTimer (aLong);
    }

    /**
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object[])} has finished.</p>
     * <p/>
     * <p>The default implementation simply invokes {@link #onCancelled()} and
     * ignores the result. If you write your own implementation, do not call
     * <code>super.onCancelled(result)</code>.</p>
     *
     * @param aLong The result, if any, computed in
     *              {@link #doInBackground(Object[])}, can be null
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @Override
    protected void onCancelled(Long aLong) {
        //super.onCancelled(aLong);
        long hkDeadTimeinMillSecond = ConvertDateToMillSec(DEADLINE);
        long milliSecondsUntilHKDir = hkDeadTimeinMillSecond - System.currentTimeMillis();
        MainActivity mainActivity = weakRef.get();
        mainActivity.startTimer (milliSecondsUntilHKDir);
    }

    private static long ConvertDateToMillSec(String givenDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }
}
