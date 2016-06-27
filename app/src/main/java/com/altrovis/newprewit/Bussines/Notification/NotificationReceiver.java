package com.altrovis.newprewit.Bussines.Notification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Raufan on 27/06/2016.
 */
public class NotificationReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName name = new ComponentName(context.getPackageName(), NotificationIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(name)));
        setResultCode(Activity.RESULT_OK);
    }
}
