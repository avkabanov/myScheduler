package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.ActionController;

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
    public void onActionClicked(ActionData actionData) {
        actionController.onViewActionBtnClicked(actionData);
        
    }

    @Override
    public void onActionLongClicked(ActionData actionData) {
        actionController.onModifyActionBtnClicked(actionData);
    }
}
