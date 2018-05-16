package com.kabanov.scheduler.actions_table;

import android.widget.TableLayout;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.UpdateActionDialog;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.utils.TimeUtils;
import com.kabanov.scheduler.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionsTableController implements ActionsTableViewController {

    private ActionsTableModel tableModel;
    private ActionsTableView tableView;
    private MainActivity mainActivity;
    private List<String> actionsListInView = new ArrayList<>();

    public ActionsTableController(MainActivity mainActivity) {
        this(mainActivity, null);
    }

    public ActionsTableController(MainActivity mainActivity, ActionsTableView actionsTableView) {
        this.mainActivity = mainActivity;

        if (actionsTableView == null) {
            tableView = new ActionsTableView(mainActivity, this);
        } else {
            tableView = actionsTableView;
        }
        tableModel = new ActionsTableModel();
    }

    public TableLayout getTableView() {
        return tableView.getActionsTable();
    }

    public void addNewAction(NewAction action) {
        ActionData actionData = createActionData(action);

        System.out.println("New action added with id: " + actionData.getId());
        tableModel.addAction(actionData);
        tableView.addRow(actionData);
        actionsListInView.add(actionData.getId());

        reorderIfRequired(actionData);
    }

    private void reorderIfRequired(ActionData actionData) {
        if (actionsListInView.size() > 1) {
            int oldIndex = actionsListInView.indexOf(actionData.getId());
            int newIndex = getNewRowPosition(actionData.getId());

            moveInActionList(oldIndex, newIndex);
            tableView.moveRow(oldIndex, newIndex);
            System.out.println("Moving action to index: " + newIndex);
        }
    }

    private void moveInActionList(int oldIndex, int newIndex) {
        Utils.switchElements(oldIndex, newIndex, actionsListInView);
    }

    private ActionData createActionData(NewAction action) {
        ActionData actionData = new ActionData(generateActionId(), action.getName(), action.getPeriodicityDays());
        actionData.setExecutedAt(action.getLastExecutedDate());
        return actionData;
    }

    private String generateActionId() {
        TimeUtils.sleepForMillisecond();
        return "action" + System.currentTimeMillis();
    }

    @Override
    public void onActionClick(String actionId) {
        updateLastExecutionTime(actionId);
    }

    private void updateLastExecutionTime(String actionId) {
        tableModel.setExecutionDateTo(actionId, new Date());
        ActionData actionData = tableModel.getAction(actionId);
        tableView.updateAction(actionId, actionData);
        reorderIfRequired(actionData);
    }

    private void actionWasRemoved(String actionId) {
        tableModel.removeAction(actionId);
        tableView.removeRow(actionId);
        actionsListInView.remove(actionId);
    }

    private void actionWasUpdated(String actionId, ActionData actionData) {
        tableView.updateAction(actionId, actionData);
        reorderIfRequired(actionData);
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

    private int getNewRowPosition(String actionId) {
        long nextDate = TimeUtils.cutWithDayAcc(tableModel.getAction(actionId).getNextExecutionDate());

        int i;
        for (i = 0; i < actionsListInView.size(); i++) {
            ActionData currAction = tableModel.getAction(actionsListInView.get(i));
            long currNextExecutionDate = TimeUtils.cutWithDayAcc(currAction.getNextExecutionDate());

            if (nextDate < currNextExecutionDate) {
                return i;
            }
        }
        return i;
    }
}