package com.kabanov.scheduler.notification;

import java.util.List;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.state.ActivityStateManager;

import android.content.Context;
import android.util.Log;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author Алексей
 * @date 09.06.2019
 */
public class NotificationWorker extends Worker {

    private static final String TAG = NotificationWorker.class.getSimpleName();

    private static List<ActionData> cache;
    private final Context context;

    public NotificationWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @Override
    public Result doWork() {

        int overdueActionsCount = 0;
        if (MainActivity.instance == null) {
            if (cache == null) {
                Log.d(TAG, "Loading activities from persistence");
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

        Log.i(TAG, "Showing notification with overdue actions count: " +
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

        return Result.success();
    }
}
