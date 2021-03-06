package com.kabanov.scheduler.broadcasts;

import java.util.List;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.notification.NotificationGenerator;
import com.kabanov.scheduler.state.converter.Converter;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.inner.InnerActivityStateManager;
import com.kabanov.scheduler.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final Logger logger = Logger.getLogger(AlarmBroadcastReceiver.class.getName());

    private InnerActivityStateManager innerActivityStateManager;
    private ApplicationState applicationState;
    private final Converter converter = new Converter();

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.info("onReceive: " + intent.getAction());

        if (context == null) return;

        NotificationController.setPeriodicalAlarmService(context);

        int overdueActionsCount = 0;
        if (MainActivity.instance == null) {
            logger.info("Loading activities from persistence");
            if (innerActivityStateManager == null) {
                innerActivityStateManager = new InnerActivityStateManager(context);
            }
            applicationState = innerActivityStateManager.loadInnerState();
            List<ActionData> actions = converter.toActionDataList(applicationState.getActionDataStateList());
            for (ActionData actionData : actions) {
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
