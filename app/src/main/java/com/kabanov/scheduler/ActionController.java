package com.kabanov.scheduler;

import android.content.Intent;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import java.util.List;

public interface ActionController {

    void setActionsTableController(ActionsTableController actionsTableController);

    void setActionsList(List<ActionData> actionData);
    
    List<ActionData> getAllActions();

    List<ActionData> getAllOverdueActions();

    void onEvent(int resultCode, Intent data) ;

    void onCreateNewActionBtnClicked();

    void onViewActionBtnClicked(ActionData actionData);

    void onModifyActionBtnClicked(ActionData actionData);
}
