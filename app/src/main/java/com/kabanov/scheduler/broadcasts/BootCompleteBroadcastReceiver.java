package com.kabanov.scheduler.broadcasts;

import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Алексей
 * @date 11.07.2019
 */
public class BootCompleteBroadcastReceiver extends BroadcastReceiver {
    private static final Logger logger = Logger.getLogger(BootCompleteBroadcastReceiver.class.getName());

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.info("onReceive: " + intent.getAction());
        NotificationController.setPeriodicalAlarmService(context);
    }
}
