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
public class MySyncTimeTask extends AsyncTask <Void, Void, Long> {

    private WeakReference <MainActivity> weakRef;
    final static String DEADLINE = "2047-07-01T00:00:00.000+0800";
    //final static String DEADLINE = "Jun 13 14:30:00 GMT+08:30 2016";

    public MySyncTimeTask(MainActivity activity) {
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
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Long doInBackground(Void... params) {
        //long now = SystemClock.;
        long milliSecondsUntilHKDir = 0;
        long hkDeadTimeinMillSecond = ConvertDateToMillSec(DEADLINE);
        long now = 0;
        long needToChange = 0;
        SntpClient sntpClient = new SntpClient();

        if (sntpClient.requestTime("time.hko.hk", 30000)) {
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
            milliSecondsUntilHKDir = hkDeadTimeinMillSecond -  System.currentTimeMillis();
        }

        //Date time = new Date(now);
        Log.i ("--->", milliSecondsUntilHKDir + " HKDIETIME ");
        return milliSecondsUntilHKDir;
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


    static long ConvertDateToMillSec (String givenDateString) {
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
