package com.kabanov.scheduler.broadcasts;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.notification.NotificationGenerator;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.persistence.BinaryStatePersistence;
import com.kabanov.scheduler.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final Logger logger = Logger.getLogger(AlarmBroadcastReceiver.class.getName());

    private BinaryStatePersistence innerActivityStateManager;
    private ApplicationState applicationState;

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.info("onReceive: " + intent.getAction());

        if (context == null) return;

        NotificationController.setPeriodicalAlarmService(context);

        int overdueActionsCount = 0;
        if (MainActivity.instance == null) {
            if (applicationState == null) {
                logger.info("Loading activities from persistence");
                if (innerActivityStateManager == null) {
                    innerActivityStateManager = new BinaryStatePersistence(context);
                }
                applicationState = innerActivityStateManager.loadInnerState();
            }
            for (ActionData actionData : applicationState.getActions()) {
                if (actionData.isOverdue()) {
                    overdueActionsCount++;
                }
            }
        } else {
            overdueActionsCount = MainActivity.instance.getActionController().getAllOverdueActions().size();
        }

        logger.info("Showing notification with overdue actions count: " + overdueActionsCount);
        try {
            NotificationGenerator.generateNotification(context, overdueActionsCount,
                    applicationState.getSettingsPersistence().getFishModeEnabled());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
