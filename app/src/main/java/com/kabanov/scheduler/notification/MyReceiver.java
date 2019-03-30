package com.kabanov.scheduler.notification;

import java.util.List;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.state.ActivityStateManager;
import com.kabanov.scheduler.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    private static final Logger logger = Logger.getLogger(MyReceiver.class.getName());


    private static List<ActionData> cache;

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.info("called receiver method");

        if (context == null) return;

        if (intent.getAction() != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                logger.info("onReceive: BOOT_COMPLETED");
                NotificationController.setPeriodicalAlarmService(context);
                return;
            }
        }

        int overdueActionsCount = 0;
        if (MainActivity.instance == null) {
            if (cache == null) {
                logger.info("Loading activities from persistence");
                ActivityStateManager activityStateManager = new ActivityStateManager(context.getFilesDir(), context);
                cache = activityStateManager.loadState().getActions();
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

        logger.info("Showing notification with overdue actions count: " +
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
