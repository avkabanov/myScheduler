package com.kabanov.scheduler.action_details;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.exceptions.ValidationException;
import com.kabanov.scheduler.utils.Utils;

public class EditActionInfo extends BaseActionInfo {
    
    @Override
    protected void enrichWithResult(Extras extras) throws ValidationException {
        ActionData initialAction = Utils.getOrThrow(getInitialAction(), IllegalStateException::new);

        extras.setActionData(new ActionData(
                initialAction.getId(),
                getActionName(),
                getExecutionInterval(),
                getLastExecutedDate()
        ));
    }

    @Override
    protected String getDialogTitle() {
        return "Edit Action";
    }

    @Override
    protected boolean isFieldsEditable() {
        return true;
    }

    @Override
    protected ButtonConfiguration getLeftButtonConfiguration() {
        return new ButtonConfiguration(
                "Delete",
                true,
                RequestedActions.DELETE
        );
    }

    @Override
    protected ButtonConfiguration getRightButtonConfiguration() {
        return new ButtonConfiguration(
                "Update",
                true,
                RequestedActions.SAVE
        );
    }
}
