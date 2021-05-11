package com.kabanov.scheduler.actions_table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionsTableModel {

    private final Map<String, ActionData> actions = new ConcurrentHashMap<>();

    public void addAction(ActionData actionData) {
        actions.put(actionData.getId(), actionData);
    }

    public ActionData getAction(String actionId) {
        ActionData actionData = actions.get(actionId);
        return new ActionData(actionData.getId(), actionData.getName(), actionData.getPeriodicityDays(), actionData.getLastExecutionDate());
    }

    public void removeAction(String actionId) {
        actions.remove(actionId);
    }

    public List<ActionData> getAllActions() {
       return new ArrayList<>(actions.values());
    }

    public void updateAction(String actionId, ActionData actionData) {
        actions.put(actionId, actionData);
    }
}
