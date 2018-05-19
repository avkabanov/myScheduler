package com.kabanov.scheduler;

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

public class ActionsControllerImpl implements ActionController {

    private final MainActivity mainActivity;
    private Map<String, ActionData> actionIdToActionMap = new HashMap<>();
    private ActionsTableController actionsTableController;
    private NotificationController notifications;

    public ActionsControllerImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        notifications = new NotificationController(mainActivity);
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
    }

    @Override
    public void updateLastExecutionTimeRequest(String actionId) {
        actionIdToActionMap.get(actionId).setExecutedAt(new Date());
        updateActionRequest(actionId, actionIdToActionMap.get(actionId));
    }

    @Override
    public List<ActionData> getAllActions() {
        return new ArrayList<>(actionIdToActionMap.values());
    }

    public void clearAll() {
        actionIdToActionMap.clear();
        actionsTableController.clearAll();
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
