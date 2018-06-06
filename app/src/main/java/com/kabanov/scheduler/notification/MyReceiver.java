package com.kabanov.scheduler.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kabanov.scheduler.MainActivity;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i("App", "called receiver method");
        int overdueActionsCount = MainActivity.instance.getActionController().
                getAllOverdueActions().size();
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
