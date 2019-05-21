package com.kabanov.scheduler.add_action;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.actions_table.ActionData;

/**
 * @author Алексей
 * @date 21.05.2019
 */
public class UpdateActionViewPresenterImpl implements UpdateActionViewPresenter {
    private ActionController actionController;

    public UpdateActionViewPresenterImpl(ActionController actionController) {
        this.actionController = actionController;
    }

    @Override
    public void onActionDeleteBtnPressed(String actionId) {
        actionController.removeActionRequest(actionId);
    }

    @Override
    public void onActionUpdateBtnPressed(String actionId, ActionData actionData) {
        actionController.updateActionRequest(actionId, actionData);
    }

    @Override
    public void onActionCompleteBtnPressed(String actionId) {
        actionController.updateLastExecutionTimeRequest(actionId);
    }
}
