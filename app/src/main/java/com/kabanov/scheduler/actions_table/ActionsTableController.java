package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.utils.Logger;

public class ActionsTableController implements ActionsTableViewController {
    private static final Logger logger = Logger.getLogger(ActionsTableController.class.getName());

    private final ActionsTableModel tableModel;
    private final ActionsTableViewImpl tableView;
    private final UpdateActionViewPresenter updateActionViewPresenter;

    public ActionsTableController(MainActivity mainActivity, 
                                  UpdateActionViewPresenter updateActionViewPresenter) {
        this(mainActivity, null, updateActionViewPresenter);
    }

    public ActionsTableController(MainActivity mainActivity, 
                                  ActionsTableViewImpl actionsTableView,
                                  UpdateActionViewPresenter updateActionViewPresenter) {
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

    @Override
    public void onActionLongClick(String actionId) {
        logger.info("On action long click: " + actionId);
        ActionData actionData = tableModel.getAction(actionId);
        updateActionViewPresenter.onActionLongClicked(actionData);
    }

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