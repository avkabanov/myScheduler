package com.kabanov.scheduler.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;

public class NotificationGenerator {

    @SuppressWarnings("static-access")
    public static void generateNotification(Context context, int numberOfOverdueActions) {
        Intent resultIntent = new Intent(context, MainActivity.class);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
        nb.setSmallIcon(R.drawable.ic_launcher_foreground);
        nb.setContentTitle("Brother , Some items are overdue!");
        nb.setContentText(numberOfOverdueActions + " items are overdue! Take a look");
        nb.setTicker("Take a look");
        nb.setAutoCancel(true);


        //get the bitmap to show in notification bar
        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText(numberOfOverdueActions + " items are overdue! Take a look");
        nb.setStyle(s);

        TaskStackBuilder TSB = TaskStackBuilder.create(context);
        TSB.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        TSB.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                TSB.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(true);

        // mId allows you to update the notification later on.
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11221, nb.build());
    }
}