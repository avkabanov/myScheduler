package com.kabanov.scheduler.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      /*Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);*/
        Log.i("App", "called receiver method");
        try{
            NotificationManager2.generateNotification(context);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
