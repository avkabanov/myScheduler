package com.kabanov.scheduler.actions_table;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionsTableModel {

    private Map<String, ActionData> actions = new HashMap<>();

    public void addAction(ActionData actionData) {
        actions.put(actionData.getId(), actionData);
    }

    public void setExecutionDateTo(String actionId, Date date) {
        actions.get(actionId).setExecutedAt(date);
    }

    public ActionData getAction(String actionId) {
        return actions.get(actionId);
    }

    public void removeAction(String actionId) {
        actions.remove(actionId);
    }

    public List<ActionData> getAllActions() {
       return new ArrayList<>(actions.values());
    }

}
