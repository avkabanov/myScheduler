package com.kabanov.scheduler.actions_table;

import android.app.Activity;
import android.content.Intent;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.action_details.BaseActionInfo;
import com.kabanov.scheduler.action_details.EditActionInfo;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.intents.RequestCode;
import com.kabanov.scheduler.utils.Logger;

public class ActionsTableController implements ActionsTableViewController {

    private static final Logger logger = Logger.getLogger(ActionsTableController.class.getName());

    private final ActionsTableModel tableModel;
    private final ActionsTableViewImpl tableView;
    private final Activity mainActivity;
    private final UpdateActionViewPresenter updateActionViewPresenter;

    public ActionsTableController(MainActivity mainActivity, 
                                  UpdateActionViewPresenter updateActionViewPresenter) {
        this(mainActivity, null, updateActionViewPresenter);
    }

    public ActionsTableController(MainActivity mainActivity, 
                                  ActionsTableViewImpl actionsTableView,
                                  UpdateActionViewPresenter updateActionViewPresenter) {

        this.mainActivity = mainActivity;
        this.updateActionViewPresenter = updateActionViewPresenter;

        if (actionsTableView == null) {
            tableView = new ActionsTableView(mainActivity, this);
        } else {
            tableView = actionsTableView;
        }
        tableModel = new ActionsTableModel();
    }

    public void addNewAction(ActionData actionData) {
        logger.info("Add new action: " + actionData);
        logger.info("Action id: " + actionData.getId());

        tableModel.addAction(actionData);
        tableView.addRow(actionData);
    }

    @Override
    public void onActionClick(String actionId) {
        logger.info("On action click: " + actionId);
        ActionData actionData = tableModel.getAction(actionId);
        updateActionViewPresenter.onActionClicked(actionData);
    }

    void showEditActionDialog(ActionData actionData) {
        //new ViewActionDialog(mainActivity, actionData, updateActionViewPresenter).show();
        Intent intent = new Intent(mainActivity, EditActionInfo.class);

        BaseActionInfo.Extras extras = new BaseActionInfo.Extras(intent);
        extras.setActionData(actionData);
        mainActivity.startActivityForResult(intent, RequestCode.ACTION_UPDATE);
    }

    @Override
    public void onActionLongClick(String actionId) {
        logger.info("On action long click: " + actionId);
        ActionData actionData = tableModel.getAction(actionId);
        //showEditActionDialog(actionData);
    }

/*
    void showEditActionDialog(ActionData actionData) {
        new EditActionDialog(mainActivity, updateActionViewPresenter, actionData).show();
    }
*/

    public void clearAll() {
        for (ActionData action : tableModel.getAllActions()) {
            tableModel.removeAction(action.getId());
            tableView.removeRow(action.getId());
        }
    }

    public void removeAction(String actionId) {
        tableModel.removeAction(actionId);
        tableView.removeRow(actionId);
    }

    public void updateAction(String actionId, ActionData actionData) {
        tableModel.updateAction(actionId, actionData);
        tableView.updateAction(actionId, actionData);
    }
}