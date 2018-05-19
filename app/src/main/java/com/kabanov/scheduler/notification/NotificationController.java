package com.kabanov.scheduler.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.kabanov.scheduler.MainActivity;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationController {

    private MainActivity activity;

    public NotificationController(MainActivity activity) {
        this.activity = activity;
    }

    public void setNotification() {
        Calendar calendar = Calendar.getInstance();



        //calendar.set(Calendar.MONTH, 8);
        //calendar.set(Calendar.YEAR, 2016);
        //calendar.set(Calendar.DAY_OF_MONTH, 18);

        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 27);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(activity, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)activity.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
}
