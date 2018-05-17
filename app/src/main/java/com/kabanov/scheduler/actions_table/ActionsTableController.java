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
import java.util.logging.Logger;

public class ActionsTableController implements ActionsTableViewController {

    private static final Logger logger = Logger.getLogger(ActionsTableController.class.getName());

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
        addNewAction(actionData);
    }

    public void addNewAction(ActionData actionData) {
        logger.info("Add new action: " + actionData);
        logger.info("Action id: " + actionData.getId());

        tableModel.addAction(actionData);
        tableView.addRow(actionData);
        actionsListInView.add(actionData.getId());

        reorderIfRequired(actionData);
    }

    private void reorderIfRequired(ActionData actionData) {
        if (actionsListInView.size() > 1) {
            int oldIndex = actionsListInView.indexOf(actionData.getId());
            actionsListInView.remove(oldIndex);
            int newIndex = getNewRowPosition(actionData.getId());

            actionsListInView.add(newIndex, actionData.getId());
            if (oldIndex != newIndex) {
                tableView.moveRow(oldIndex, newIndex);
                logger.info("Reordering: name=" + actionData.getName() + " oldIndex=" + oldIndex + " newIndex=" + newIndex);
            }
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
        logger.info("On action click: " + actionId);
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
        logger.info("On action long click: " + actionId);
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

    @Override
    public void clearAll() {
        for (ActionData action : tableModel.getAllActions()) {
            tableModel.removeAction(action.getId());
            tableView.removeRow(action.getId());
            actionsListInView.remove(action.getId());
        }
    }

    private int getNewRowPosition(String actionId) {
        long nextDateForCurrAction = TimeUtils.cutWithDayAcc(tableModel.getAction(actionId).getNextExecutionDate());

        int i;
        for (i = 0; i < actionsListInView.size(); i++) {
            ActionData currAction = tableModel.getAction(actionsListInView.get(i));
            long nextExecutionDateOfItem = TimeUtils.cutWithDayAcc(currAction.getNextExecutionDate());

            if (nextDateForCurrAction < nextExecutionDateOfItem) {
                return i;
            }
        }
        return i;


       /* if (actionsListInView.get(newPosition).equals(actionId)) {
            return newPosition;
        } else {
            if (actionId.equals(actionsListInView.get(newPosition - 1))) {
                return newPosition - 1 - 1; // return old position
            }
        }*/




        // if previous or next element is the same, newPosition is omitted.
        // if newPosition is in the end and element is the same as the last one
        /*if (newPosition == actionsListInView.size()) {
            if (actionId.equals(actionsListInView.get(actionsListInView.size() - 1))) {
                return actionsListInView.size() - 1; // return old position
            }
        } else if(newPosition > 0) {
            if (actionId.equals(actionsListInView.get(newPosition - 1))) {
                return newPosition - 1;
            } else if(actionId.equals(actionsListInView.get(newPosition + 1))) {
                return newPosition + 1;
            }
        }*/
    }

    private int calculateNewPosition(String actionId) {
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

    public List<ActionData> getAllActions() {
        return tableModel.getAllActions();
    }
}