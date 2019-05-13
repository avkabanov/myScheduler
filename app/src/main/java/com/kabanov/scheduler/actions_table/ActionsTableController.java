package com.kabanov.scheduler.actions_table;

import java.util.ArrayList;
import java.util.List;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.action_details.dialogs.EditActionDialog;
import com.kabanov.scheduler.action_details.dialogs.ViewActionDialog;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.utils.Logger;
import com.kabanov.scheduler.utils.TimeUtils;

import android.widget.TableLayout;

public class ActionsTableController implements ActionsTableViewController {

    private static final Logger logger = Logger.getLogger(ActionsTableController.class.getName());

    private ActionsTableModel tableModel;
    private ActionsTableView tableView;
    private MainActivity mainActivity;
    private final ActionController actionController;
    private List<String> actionsListInView = new ArrayList<>();
    private UpdateActionViewPresenter updateActionViewPresenter = new UpdateActionViewPresenter() {
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
    };

    public ActionsTableController(MainActivity mainActivity, ActionController actionController) {
        this(mainActivity, null, actionController);
    }

    public ActionsTableController(MainActivity mainActivity, ActionsTableView actionsTableView,
                                  ActionController actionController) {
        this.mainActivity = mainActivity;
        this.actionController = actionController;

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
                logger.info(
                        "Reordering: name=" + actionData.getName() + " oldIndex=" + oldIndex + " newIndex=" + newIndex);
            }
        }
    }

    @Override
    public void onActionClick(String actionId) {
        logger.info("On action click: " + actionId);
        ActionData actionData = tableModel.getAction(actionId);
        showViewActionDialog(actionData);
    }

    void showViewActionDialog(ActionData actionData) {
        new ViewActionDialog(mainActivity, actionData, updateActionViewPresenter).show();
    }

    @Override
    public void onActionLongClick(String actionId) {
        logger.info("On action long click: " + actionId);
        ActionData actionData = tableModel.getAction(actionId);
        showEditActionDialog(actionData);
    }

    void showEditActionDialog(ActionData actionData) {
        new EditActionDialog(mainActivity, updateActionViewPresenter, actionData).show();
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
    }

    public void removeAction(String actionId) {
        tableModel.removeAction(actionId);
        tableView.removeRow(actionId);
        actionsListInView.remove(actionId);
    }

    public void updateAction(String actionId, ActionData actionData) {
        tableView.updateAction(actionId, actionData);
        reorderIfRequired(actionData);
    }
}