package com.kabanov.scheduler.actions_table;

import android.widget.TableLayout;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.NewAction;

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
        return new ActionData(generateActionId(), action.getName(), action.getDates());
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

    @Override
    public void onActionLongClick(String actionId) {

    }
}
