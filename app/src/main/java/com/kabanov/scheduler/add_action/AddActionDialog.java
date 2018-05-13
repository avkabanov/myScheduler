package com.kabanov.scheduler.add_action;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.utils.Callback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActionDialog {

    private final MainActivity activity;
    private View popupView;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");

    public AddActionDialog(MainActivity activity, final Callback<NewAction> callback) {
        this.activity = activity;

        LayoutInflater inflater = activity.getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle("Add New Action");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        popupView = inflater.inflate(R.layout.add_action_popup, null);

        ((TextView) popupView.findViewById(R.id.executed_last_time_txt))
                .setText(formatLastExecutedDate(new Date()));
        builder.
                setView(popupView)
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NewAction newAction;

                        try {
                            newAction = getNewAction();
                        } catch (ValidationException e) {
                            showErrorDialog(e.getMessage());
                            
                            return;
                        }
                        callback.onCallback(newAction);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
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

    public NewAction getNewAction() throws ValidationException {
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
        return new NewAction(
                actionName.getText().toString(),
                Integer.parseInt(actionDate.getText().toString()),
                lastExecutedDate
        );

    }

    private String formatLastExecutedDate(Date date) {
        return dateFormatter.format(date);
    }

    private Date parseLastExecutedDate(String string) throws ParseException {
        return dateFormatter.parse(string);
    }


}
