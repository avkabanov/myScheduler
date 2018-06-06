package com.kabanov.scheduler;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;

import java.util.List;

public interface ActionController {

    void setActionsTableController(ActionsTableController actionsTableController);

    void addActionRequest(ActionData actionData) throws ValidationException;

    void addActionRequest(NewAction newAction) throws ValidationException;

    void removeActionRequest(String actionId);

    void updateActionRequest(String actionId, ActionData actionData);

    void updateLastExecutionTimeRequest(String actionId);

    List<ActionData> getAllActions();

    void clearAll();

    List<ActionData> getAllOverdueActions();
}
