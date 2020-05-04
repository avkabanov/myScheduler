package com.kabanov.scheduler.notification;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.notification.title.NotificationMessageGenerator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationGenerator {

    private static final NotificationMessageGenerator notificationMessageGenerator = new NotificationMessageGenerator();
    private static final boolean IS_FISH_MODE = true;

    @SuppressWarnings("static-access")
    public static void generateNotification(Context context, int numberOfOverdueActions) {

        TitleContentHolder message;
        if (numberOfOverdueActions == 0) {
            message = generateNoOverdueActionsMessage();
        } else {
            message = generateOverdueActionsMessage(numberOfOverdueActions);
        }

        displayNotification(context, message.title, message.content);
    }

    private static TitleContentHolder generateOverdueActionsMessage(int numberOfOverdueActions) {
        String title;
        String content;
        if (IS_FISH_MODE) {
            title = "Fish! Some items are overdue!";
            content = String.format("%s items are overdue! Complete them immediately", numberOfOverdueActions);
        } else {
            title = "Brother! Some items are overdue!";
            content = String.format("%s items are overdue! Take a look", numberOfOverdueActions);        
        }
        return new TitleContentHolder(title, content);        
    }

    private static TitleContentHolder generateNoOverdueActionsMessage() {
        String title;
        String content;
        
        if (IS_FISH_MODE) {
            title = "Good fish! No items are overdue!";
            content = notificationMessageGenerator.generate();
        } else {
            title = "Brother! No items are overdue! Respect!";
            content = null;
        }
        return new TitleContentHolder(title, content);
    }

    private static void displayNotification(Context context, String title, String contentText) {
        Intent resultIntent = new Intent(context, MainActivity.class);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText));
        
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);

        notificationManager.notify(notificationId, mBuilder.build());
    }
    
    private static class TitleContentHolder {
        private final String title;
        private final String content;

        public TitleContentHolder(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
