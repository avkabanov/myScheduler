package com.kabanov.scheduler.actions_table;

import android.widget.TableLayout;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.UpdateActionDialog;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;

import java.util.Date;

public class ActionsTableController implements ActionsTableViewController {

    private ActionsTableModel tableModel;
    private ActionsTableView tableView;
    private MainActivity mainActivity;

    public ActionsTableController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        tableView = new ActionsTableView(mainActivity, this);
        tableModel = new ActionsTableModel();
    }

    public TableLayout getTableView() {
        return tableView.getActionsTable();
    }

    public void addNewAction(NewAction action) {
        ActionData actionData = createActionData(action);

        tableModel.addAction(actionData);
        tableView.addRow(actionData);

    }

    private ActionData createActionData(NewAction action) {
        ActionData actionData = new ActionData(generateActionId(), action.getName(), action.getDates());
        actionData.setExecutedAt(action.getLastExecutedDate());
        return actionData;
    }

    private String generateActionId() {
        return "action" + System.currentTimeMillis();
    }

    @Override
    public void onActionClick(String actionId) {
        tableModel.setExecutionDateTo(actionId, new Date());

        ActionData actionData = tableModel.getAction(actionId);
        tableView.updateAction(actionId, actionData);
    }

    private void actionWasRemoved(String actionId) {
        tableModel.removeAction(actionId);
        tableView.removeRow(actionId);
    }

    private void actionWasUpdated(String actionId, ActionData actionData) {
        tableView.updateAction(actionId, actionData);
    }

    @Override
    public void onActionLongClick(String actionId) {
        ActionData actionData = tableModel.getAction(actionId);
        new UpdateActionDialog(mainActivity, new UpdateActionViewPresenter() {
            @Override
            public void onActionDeleteBtnPressed(String actionId) {
                ActionsTableController.this.actionWasRemoved(actionId);
            }

            @Override
            public void onActionUpdateBtnPressed(String actionId, ActionData actionData) {
                ActionsTableController.this.actionWasUpdated(actionId, actionData);
            }
        }, actionData);

    }
}
