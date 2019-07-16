package com.kabanov.scheduler.notification;

import java.util.Date;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.broadcasts.AlarmBroadcastReceiver;
import com.kabanov.scheduler.utils.Logger;
import com.kabanov.scheduler.utils.TimeUtils;

import static android.content.Context.ALARM_SERVICE;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationController {

    private static final Logger logger = Logger.getLogger(NotificationController.class.getName());

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    

    public NotificationController(MainActivity activity) {
        setPeriodicalAlarmService(activity);
    }

    private static boolean nowIsAfter10AM() {
        return TimeUtils.isAfter10AM(new Date());
    }

    public static void setPeriodicalAlarmService(Context activity) {
        logger.info("Setting alarm service");
        Intent myIntent = new Intent(activity, AlarmBroadcastReceiver.class);
        
        pendingIntent = PendingIntent.getBroadcast(activity, 0, myIntent, 0);
        alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        
        Date nextAlarmDay = nowIsAfter10AM() ? TimeUtils.addDays(new Date(), 1) : new Date();

        long notificationTime = TimeUtils.getTime10AMGivenDay(nextAlarmDay);
        int alarmType = AlarmManager.RTC_WAKEUP;
        alarmManager.setExactAndAllowWhileIdle(alarmType, notificationTime, pendingIntent);;
        
        logger.info("Set alarm manager to fire at " + TimeUtils.toReadableTime(notificationTime));
    }
}
