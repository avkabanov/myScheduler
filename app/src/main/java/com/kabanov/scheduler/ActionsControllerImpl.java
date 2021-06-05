package com.kabanov.scheduler;

import static android.app.Activity.RESULT_CANCELED;
import android.content.Intent;
import com.kabanov.scheduler.action_details.BaseActionInfo;
import com.kabanov.scheduler.action_details.CreateActionInfo;
import com.kabanov.scheduler.action_details.EditActionInfo;
import com.kabanov.scheduler.action_details.ViewActionInfo;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.intents.RequestCode;
import com.kabanov.scheduler.utils.TimeUtils;
import com.kabanov.scheduler.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ActionsControllerImpl implements ActionController {

    private static final Logger logger = Logger.getLogger(ActionsControllerImpl.class.getName());

    private final Map<String, ActionData> actionIdToActionMap = new HashMap<>();
    private final MainActivity mainActivity;
    private ActionsTableController actionsTableController;

    public ActionsControllerImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void setActionsTableController(ActionsTableController actionsTableController) {
        this.actionsTableController = actionsTableController;
    }

    @Override
    public void addActionRequest(ActionData actionData) {
        actionIdToActionMap.put(actionData.getId(), actionData);
        actionsTableController.addNewAction(actionData);
    }

    @Override
    public void addActionRequest(NewAction newAction) {
        ActionData actionData = createActionData(newAction);
        addActionRequest(actionData);
    }

    @Override
    public void removeActionRequest(String actionId) {
        actionIdToActionMap.remove(actionId);
        actionsTableController.removeAction(actionId);
    }

    @Override   // TODO do we need actionID here?
    public void updateActionRequest(String actionId, ActionData actionData) {
        actionData.setLastUpdateExecutionTime(null);
        actionsTableController.updateAction(actionId, actionData);
        actionIdToActionMap.put(actionId, actionData);
    }

    @Override
    public void updateLastExecutionTimeRequest(String actionId) {
        ActionData action = actionIdToActionMap.get(actionId);
        if (actionCanBeUpdated(action)) {
            action.setExecutedAt(new Date());
            action.setLastUpdateExecutionTime(new Date());
            updateActionRequest(actionId, actionIdToActionMap.get(actionId));
        } else {
            logger.info("Update last execution time for action "
                    + actionId + " is spamming");
        }
    }

    // update should fire not more often than once per 1 minute. Otherwise that will not make sense
    private boolean actionCanBeUpdated(ActionData action) {
        return action.getLastUpdateExecutionTime() == null ||
                action.getLastUpdateExecutionTime() != null &&
                new Date().getTime() - action.getLastUpdateExecutionTime().getTime() > TimeUnit.MINUTES.toMillis(1);
    }

    @Override
    public List<ActionData> getAllActions() {
        return new ArrayList<>(actionIdToActionMap.values());
    }

    @Override
    public void clearAll() {
        actionIdToActionMap.clear();
        actionsTableController.clearAll();
    }

    @Override
    public List<ActionData> getAllOverdueActions() {
        return actionIdToActionMap.values().stream().filter(input -> input != null && input.isOverdue()).collect(Collectors.toList());
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
    public void onEvent(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        
        BaseActionInfo.Extras extras = new BaseActionInfo.Extras(data);
        
        switch (extras.getRequestedAction()) {
            
            case CREATE: {
                NewAction newAction = Utils.getOrThrow(extras.getNewAction(), IllegalStateException::new);
                addActionRequest(newAction);
                break;
            }
            
            case COMPLETE: {
                ActionData actionData = Utils.getOrThrow(extras.getActionData(), IllegalStateException::new);
                updateLastExecutionTimeRequest(actionData.getId());
                break;
            }
            
            case EDIT: {
                ActionData actionData = Utils.getOrThrow(extras.getActionData(), IllegalStateException::new);
                showModifyActionDialog(actionData);
                break;
            }
            
            case DELETE: {
                ActionData actionData = Utils.getOrThrow(extras.getActionData(), IllegalStateException::new);
                removeActionRequest(actionData.getId());
                break;
            }
            case SAVE:
                ActionData actionData = Utils.getOrThrow(extras.getActionData(), IllegalStateException::new);
                updateActionRequest(actionData.getId(), actionData);
                break;
        }
    }

    void showViewActionDialog(ActionData actionData) {
        Intent intent = new Intent(mainActivity, ViewActionInfo.class);
        BaseActionInfo.Extras extras = new BaseActionInfo.Extras(intent);
        extras.setActionData(actionData);
        
        mainActivity.startActivityForResult(intent, RequestCode.ACTION_UPDATE);
    }

    @Override
    public void showModifyActionDialog(ActionData actionData) {
        Intent intent = new Intent(mainActivity, EditActionInfo.class);

        BaseActionInfo.Extras extras = new BaseActionInfo.Extras(intent);
        extras.setActionData(actionData);
        mainActivity.startActivityForResult(intent, RequestCode.ACTION_UPDATE);
    }
    
    @Override
    public void showCreateNewActionDialog() {
        Intent intent = new Intent(mainActivity, CreateActionInfo.class);
        mainActivity.startActivityForResult(intent, RequestCode.ACTION_UPDATE);
    }

    @Override
    public void onActionClicked(ActionData actionData) {
        showViewActionDialog(actionData);
    }
}
