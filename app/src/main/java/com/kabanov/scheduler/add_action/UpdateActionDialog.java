package com.kabanov.scheduler.add_action;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.actions_table.ActionData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateActionDialog {

    private final MainActivity activity;
    private final View popupView;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");


    public UpdateActionDialog(MainActivity activity, final UpdateActionViewPresenter viewPresenter, final ActionData actionData) {
        this.activity = activity;

        LayoutInflater inflater = activity.getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle("Edit Action");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        popupView = inflater.inflate(R.layout.add_action_popup, null);

        fillPopupView(actionData);
        builder
                .setView(popupView)
                .setNeutralButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewPresenter.onActionDeleteBtnPressed(actionData.getId());
                    }
                })
                // Set the action buttons
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            updateActionData(actionData);
                        } catch (ValidationException e) {
                            showErrorDialog(e.getMessage());

                            return;
                        }
                        viewPresenter.onActionUpdateBtnPressed(actionData.getId(), actionData);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
    }



    private void fillPopupView(ActionData actionData) {
        ((TextView) popupView.findViewById(R.id.executed_last_time_txt))
                .setText(formatLastExecutedDate(actionData.getLastExecutionDate()));

        ((TextView) popupView.findViewById(R.id.action_name_txt)).setText(actionData.getName());
        ((TextView) popupView.findViewById(R.id.days_number_txt)).setText(String.valueOf(actionData.getPeriodicityDays()));
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void updateActionData(ActionData actionData) throws ValidationException {
        TextView actionName = popupView.findViewById(R.id.action_name_txt);
        TextView actionDate = popupView.findViewById(R.id.days_number_txt);
        TextView lastExecutedDateTV = popupView.findViewById(R.id.executed_last_time_txt);

        String dateToParse = lastExecutedDateTV.getText().toString();
        Date lastExecutedDate;

        try {
            lastExecutedDate = parseLastExecutedDate(dateToParse);
        } catch (ParseException e) {
            throw new ValidationException("Can not parse date: " + dateToParse);

        }

        actionData.setName(actionName.getText().toString());
        actionData.setPeriodicityDays(Integer.parseInt(actionDate.getText().toString()));
        actionData.setExecutedAt(lastExecutedDate);

    }

    private String formatLastExecutedDate(Date date) {
        return dateFormatter.format(date);
    }

    private Date parseLastExecutedDate(String string) throws ParseException {
        return dateFormatter.parse(string);
    }
}
