package com.altrovis.newprewit.Bussines.Notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.altrovis.newprewit.ActivityLogin;
import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.R;

/**
 * Created by Raufan on 27/06/2016.
 */
public class NotificationIntentService extends IntentService {
    public NotificationIntentService() {
        super(NotificationIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            String message = extras.getString("message");
            String type = extras.getString("type");
            String workItemID = extras.getString("workItemID");

            if (workItemID != null) {
                message = Html.fromHtml(message).toString();

                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent newIntent = new Intent(this, ActivityMain.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, 0);

                String title = "PREWIT";
                if (type.equalsIgnoreCase("1")) {
                    title = "New Work Item";

                } else if (type.equalsIgnoreCase("2")) {
                    title = "Work Item Updated";

                } else if (type.equalsIgnoreCase("3")) {
                    title = "Work Item Description Updated";

                } else if (type.equalsIgnoreCase("4")) {
                    title = "Work Item Estimation Updated";

                } else if (type.equalsIgnoreCase("5")) {
                    title = "Completed Work Item";

                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_prewit).setContentTitle(title)
                        .setContentText(message).setStyle(new NotificationCompat.BigTextStyle().bigText(message)).setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                notificationManager.notify(GlobalVariable.NotificationID, builder.build());
            }
        }
        NotificationReceiver.completeWakefulIntent(intent);
    }
}
