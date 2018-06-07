package com.kabanov.scheduler;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActionsControllerImpl implements ActionController {

    private Map<String, ActionData> actionIdToActionMap = new HashMap<>();
    private ActionsTableController actionsTableController;

    public ActionsControllerImpl(MainActivity mainActivity) {
        new NotificationController(mainActivity);
    }

    public void setActionsTableController(ActionsTableController actionsTableController) {
        this.actionsTableController = actionsTableController;
    }

    @Override
    public void addActionRequest(ActionData actionData) throws ValidationException {
        actionIdToActionMap.put(actionData.getId(), actionData);
        actionsTableController.addNewAction(actionData);
    }

    @Override
    public void addActionRequest(NewAction newAction) throws ValidationException {
        ActionData actionData = createActionData(newAction);
        addActionRequest(actionData);
    }

    @Override
    public void removeActionRequest(String actionId) {
        actionIdToActionMap.remove(actionId);
        actionsTableController.removeAction(actionId);
    }

    @Override
    public void updateActionRequest(String actionId, ActionData actionData) {
        actionsTableController.updateAction(actionId, actionData);
        actionData.setLastUpdateExecutionTime(null);
    }

    @Override
    public void updateLastExecutionTimeRequest(String actionId) {
        ActionData action = actionIdToActionMap.get(actionId);
        if (actionCanBeUpdated(action)) {
            action.setExecutedAt(new Date());
            action.setLastUpdateExecutionTime(new Date());
            updateActionRequest(actionId, actionIdToActionMap.get(actionId));
        } else {
            Log.d("ActionControllerImpl", "Update last execution time for action "
                    + actionId + " is spamming");
        }
    }

    // update should fire not more often than once per 1 minute. Otherwise that will not make sense
    private boolean actionCanBeUpdated(ActionData action) {
        return action.getLastUpdateExecutionTime() != null &&
                new Date().getTime() - action.getLastUpdateExecutionTime().getTime() > TimeUnit.MINUTES.toMillis(1);
    }

    @Override
    public List<ActionData> getAllActions() {
        return new ArrayList<>(actionIdToActionMap.values());
    }

    public void clearAll() {
        actionIdToActionMap.clear();
        actionsTableController.clearAll();
    }

    @Override
    public List<ActionData> getAllOverdueActions() {
        return FluentIterable.from(actionIdToActionMap.values())
                .filter(new Predicate<ActionData>() {
                    @Override
                    public boolean apply(@Nullable ActionData input) {
                        return input != null && input.isOverdue();
                    }
                })
                .toList();
    }

    private ActionData createActionData(NewAction action) {
        ActionData actionData = new ActionData(generateActionId(), action.getName(), action.getPeriodicityDays());
        actionData.setExecutedAt(action.getLastExecutedDate());
        return actionData;
    }

    private String generateActionId() {
        TimeUtils.sleepForMillisecond();
        return "action" + System.currentTimeMillis();
    }
}
