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

public class NotificationManager2 {

    @SuppressWarnings("static-access")
    public static void generateNotification(Context context){

        NotificationCompat.Builder nb= new NotificationCompat.Builder(context);
        nb.setSmallIcon(R.drawable.ic_launcher_foreground);
        nb.setContentTitle("Brother , Have your read hadish today ?");
        nb.setContentText("Hadish can makes our life Enlightened");
        nb.setTicker("Take a look");

        nb.setAutoCancel(true);



        //get the bitmap to show in notification bar
        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText("Hadish can makes our life Enlightened");
        nb.setStyle(s);



        Intent resultIntent = new Intent(context, MainActivity.class);
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
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11221, nb.build());
    }
}
