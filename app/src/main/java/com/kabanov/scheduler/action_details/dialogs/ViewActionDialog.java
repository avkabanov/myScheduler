package com.kabanov.scheduler.action_details.dialogs;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;

import android.support.annotation.NonNull;

public class ViewActionDialog extends BaseActionDetailsDialog {


    public ViewActionDialog(MainActivity activity, final ActionData actionData,
                            final UpdateActionViewPresenter viewPresenter) {
        super(activity);
        builder
                .setNeutralButton("Complete", (dialog, id) -> {
                    viewPresenter.onActionCompleteBtnPressed(actionData.getId());
                })
                .setNegativeButton("Edit", ((dialog, which) -> {
                    new EditActionDialog(activity, viewPresenter, actionData).show();
                }))
                .setPositiveButton("Close", (dialog, id) -> {    
                });

        fillDialog(actionData);
        setReadOnly(true);
    }

    @NonNull
    @Override
    String getDialogTitle() {
        return "View Action";
    }
}
