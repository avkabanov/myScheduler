package com.kabanov.scheduler.action_details;

import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;

public class CreateActionInfo extends BaseActionInfo {
    
    @Override
    protected void enrichWithResult(Extras extras) throws ValidationException {
        extras.setNewAction(new NewAction(
                getActionName(),
                getExecutionInterval(),
                getLastExecutedDate()
        ));        
    }

    @Override
    protected String getDialogTitle() {
        return "Create Action";
    }

    @Override
    protected boolean isFieldsEditable() {
        return true;
    }

    @Override
    protected ButtonConfiguration getLeftButtonConfiguration() {
        return new ButtonConfiguration(
                "Empty",
                false,  // TODO
                RequestedActions.COMPLETE
        );
    }

    @Override
    protected ButtonConfiguration getRightButtonConfiguration() {
        return new ButtonConfiguration(
                "Create",
                true,
                RequestedActions.CREATE
        );
    }
}
