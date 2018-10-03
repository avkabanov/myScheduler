package com.kabanov.scheduler.notification;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.utils.Logger;

import static android.content.Context.ALARM_SERVICE;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationController {

    private static final Logger logger = Logger.getLogger(NotificationController.class.getName());

    private MainActivity activity;
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    

    public NotificationController(MainActivity activity) {
        this.activity = activity;
        setPeriodicalAlarmService(activity);
    }

    private static long getAt10AM() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 10);
        return calendar.getTime().getTime();
    }

    public static void setPeriodicalAlarmService(Context activity) {
        logger.info("Setting alarm service");
        Intent myIntent = new Intent(activity, MyReceiver.class);
        
        pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);
        alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, getAt10AM(), TimeUnit.DAYS.toMillis(1), pendingIntent);

        logger.info("Set alarm manager to fire");
    }

    public void stopAlarmService() {
        logger.debug("Stopping alarm service");
        alarmManager.cancel(pendingIntent);
        logger.debug("Alarm service stopped");
    }

    private void setSingleAlarm() {
        Calendar calendar = Calendar.getInstance();

        //calendar.set(Calendar.MONTH, 8);
        //calendar.set(Calendar.YEAR, 2016);
        //calendar.set(Calendar.DAY_OF_MONTH, 18);

        calendar.setTime(new Date());
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 0);
        //calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(activity, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
}
