package com.trx.hkdcountdown;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by TRX on 06/15/2016.
 */
public class HKDWidgetProvider extends AppWidgetProvider {
    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_UPDATE} and
     * {@link AppWidgetManager#ACTION_APPWIDGET_RESTORED} broadcasts when this AppWidget
     * provider is being asked to provide {@link RemoteViews RemoteViews}
     * for a set of AppWidgets.  Override this method to implement your own AppWidget functionality.
     * <p/>
     * {@more}
     *
     * @param context          The {@link Context Context} in which this receiver is
     *                         running.
     * @param appWidgetManager A {@link AppWidgetManager} object you can call {@link
     *                         AppWidgetManager#updateAppWidget} on.
     * @param appWidgetIds     The appWidgetIds for which an update is needed.  Note that this
     *                         may be all of the AppWidget instances for this provider, or just
     *                         a subset of them.
     * @see AppWidgetManager#ACTION_APPWIDGET_UPDATE
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setTextViewText(R.id.textView, number);

            Intent intent = new Intent(context, HKDWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }



        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
