package com.kabanov.scheduler.actions_table;

import java.util.HashMap;
import java.util.Map;

public class ActionsTableModel {

    private Map<String, ActionData> actions = new HashMap<>();

    public void addAction(ActionData actionData) {
        actions.put(actionData.getId(), actionData);
    }
}
