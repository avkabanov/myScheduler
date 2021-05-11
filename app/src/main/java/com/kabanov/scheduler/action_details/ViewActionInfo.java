package com.kabanov.scheduler.action_details;

import com.kabanov.scheduler.utils.Utils;

public class ViewActionInfo extends BaseActionInfo {
    
    @Override
    protected void enrichWithResult(Extras extras) {
        extras.setActionData(Utils.getOrThrow(getInitialAction(), IllegalStateException::new));    
    }

    @Override
    protected String getDialogTitle() {
        return "View Action";
    }

    @Override
    protected boolean isFieldsEditable() {
        return false;
    }

    @Override
    protected ButtonConfiguration getLeftButtonConfiguration() {
        return new ButtonConfiguration(
                "Complete",
                true,
                RequestedActions.COMPLETE
        );
    }

    @Override
    protected ButtonConfiguration getRightButtonConfiguration() {
        return new ButtonConfiguration(
                "Edit",
                true,
                RequestedActions.EDIT
        );
    }
}
