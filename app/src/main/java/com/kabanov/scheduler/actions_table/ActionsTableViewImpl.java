package com.kabanov.scheduler.actions_table;

public interface ActionsTableViewImpl {
    void addRow(ActionData action);

    void updateAction(String actionId, ActionData actionData);

    void removeRow(String actionId);
}
