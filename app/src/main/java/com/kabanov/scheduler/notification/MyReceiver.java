package com.kabanov.scheduler.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.saver.ActivityStateManager;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {

    private static List<ActionData> cache;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("App", "called receiver method");

        if (context == null) return;

        if (intent.getAction() != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(MyReceiver.class.getName(), "onReceive: BOOT_COMPLETED");
                NotificationController.setPeriodicalAlarmService(context);
            }
        }

        int overdueActionsCount = 0;
        if (MainActivity.instance == null) {
            if (cache == null) {
                Log.d("MyReceiver", "Loading activities from persistence");
                ActivityStateManager activityStateManager = new ActivityStateManager(context.getFilesDir());
                cache = activityStateManager.loadActions();
            }
            for (ActionData actionData : cache) {
                if (actionData.isOverdue()) {
                    overdueActionsCount++;
                }
            }

        } else {
            overdueActionsCount = MainActivity.instance.getActionController().
                    getAllOverdueActions().size();
        }

        Log.d("MyReceiver", "Showing notification with overdue actions count: " +
                overdueActionsCount);
        // TODO this line should be uncommented
        /*
        if (overdueActionsCount == 0) {
            return;
        }*/
        try {
            NotificationGenerator.generateNotification(context, overdueActionsCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
