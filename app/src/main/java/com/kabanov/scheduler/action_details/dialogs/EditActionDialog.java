package com.kabanov.scheduler.action_details.dialogs;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.support.annotation.NonNull;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.utils.ActionUtils;

public class EditActionDialog extends BaseActionDetailsDialog {

    public EditActionDialog(Activity activity, final UpdateActionViewPresenter viewPresenter, final ActionData actionData) {
        super(activity);

        fillDialog(actionData);
        builder
                .setNeutralButton("Remove",
                        (dialog, which) -> viewPresenter.onActionDeleteBtnPressed(actionData.getId()))
                // Set the action buttons
                .setPositiveButton("Update", (dialog, id) -> {

                    try {
                        NewAction data = getAction();
                        ActionUtils.validateFieldsNotEmpty(data);
                        actionData.setName(data.getName());
                        actionData.setExecutedAt(data.getLastExecutedDate());
                        actionData.setPeriodicityDays(data.getPeriodicityDays());
                    } catch (ValidationException e) {
                        showErrorDialog(e.getMessage());
                        return;
                    }
                    viewPresenter.onActionUpdateBtnPressed(actionData.getId(), actionData);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });
    }

    @NonNull
    @Override
    String getDialogTitle() {
        return "Edit Action";
    }
}
