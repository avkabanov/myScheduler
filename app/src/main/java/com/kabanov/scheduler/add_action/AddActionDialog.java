package com.kabanov.scheduler.add_action;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.utils.Callback;

public class AddActionDialog {

    private View popupView;

    public AddActionDialog(MainActivity activity, final Callback<NewAction> callback) {

        LayoutInflater inflater = activity.getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle("Add New Action");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        popupView = inflater.inflate(R.layout.add_action_popup, null);
        builder.
                setView(popupView)
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        callback.onCallback(getNewAction());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
    }

    public NewAction getNewAction() {
        TextView actionName = popupView.findViewById(R.id.action_name_txt);
        TextView actionDate = popupView.findViewById(R.id.days_number_txt);

        return new NewAction(
                actionName.getText().toString(),
                Integer.parseInt(actionDate.getText().toString())
        );

    }


}
