package com.kabanov.scheduler.action_details.dialogs;

import android.support.annotation.NonNull;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.utils.ActionUtils;
import com.kabanov.scheduler.utils.Callback;
import java.util.Date;

public class AddActionDialog extends BaseActionDetailsDialog {

    public AddActionDialog(MainActivity activity, final Callback<NewAction> callback) {
        super(activity);

        builder
                .setPositiveButton("Add", (dialog, id) -> {
                    NewAction newAction;
                    try {
                        newAction = getAction();
                        ActionUtils.validateFieldsNotEmpty(newAction);
                    } catch (ValidationException e) {
                        showErrorDialog(e.getMessage());
                        return;
                    }
                    callback.onCallback(newAction);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });
        
        setLastExecutedDate(new Date());
    }
    
    @NonNull
    @Override
    String getDialogTitle() {
        return "Add New Action";
    }

}
