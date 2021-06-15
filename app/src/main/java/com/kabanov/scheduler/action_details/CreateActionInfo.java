package com.kabanov.scheduler.action_details;

import com.kabanov.scheduler.data.NewAction;
import com.kabanov.scheduler.exceptions.ValidationException;
import java.util.Date;

public class CreateActionInfo extends BaseActionInfo {
    
    @Override
    protected void enrichWithResult(Extras extras) throws ValidationException {
        String actionName = getActionName();
        validateActionName(actionName);
        Integer executionInterval = getExecutionInterval();
        validateExecutionInterval(executionInterval);
        Date lastExecutedDate = getLastExecutedDate();
        
        validateLastExecutionDate(lastExecutedDate);
        extras.setNewAction(new NewAction(
                actionName,
                executionInterval,
                lastExecutedDate
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
