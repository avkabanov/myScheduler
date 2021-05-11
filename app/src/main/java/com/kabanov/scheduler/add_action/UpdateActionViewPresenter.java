package com.kabanov.scheduler.add_action;

import com.kabanov.scheduler.actions_table.ActionData;

public interface UpdateActionViewPresenter {

    void onActionDeleteBtnPressed(String actionId);

    void onActionUpdateBtnPressed(String actionId, ActionData actionData);

    void onActionCompleteBtnPressed(String actionId);

    void onActionClicked(ActionData actionData);
}
