package com.kabanov.scheduler.action_details.dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.common.base.Strings;
import com.kabanov.scheduler.MainActivity;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.add_action.NewAction;
import com.kabanov.scheduler.add_action.ValidationException;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Алексей
 * @date 07.12.2018
 */
public class BaseActionDetailsDialog {

    protected final MainActivity activity;
    private final View popupView;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");
    protected final AlertDialog.Builder builder;

    final Calendar myCalendar = Calendar.getInstance();


    public BaseActionDetailsDialog(MainActivity activity) {
        this.activity = activity;

        LayoutInflater inflater = activity.getLayoutInflater();

        builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(getDialogTitle());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        popupView = inflater.inflate(R.layout.add_action_popup, null);

        EditText edittext= popupView.findViewById(R.id.executed_last_time_txt);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        

        edittext.setOnClickListener(v -> {
            new DatePickerDialog(activity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        builder.setView(popupView);
    }

    private void updateLabel() {
        ((TextView) popupView.findViewById(R.id.executed_last_time_txt))
                .setText(formatLastExecutedDate(myCalendar.getTime()));
    }
    
    public void show() {
        builder.create().show();
    }

    void fillDialog(ActionData actionData) {
        ((TextView) popupView.findViewById(R.id.executed_last_time_txt))
                .setText(formatLastExecutedDate(actionData.getLastExecutionDate()));

        ((TextView) popupView.findViewById(R.id.action_name_txt)).setText(actionData.getName());
        ((TextView) popupView.findViewById(R.id.days_number_txt))
                .setText(String.valueOf(actionData.getPeriodicityDays()));
    }

    @NonNull
    String getDialogTitle() {
        return "Edit Action";
    }

    private String formatLastExecutedDate(Date date) {
        return dateFormatter.format(date);
    }

    private Date parseLastExecutedDate(String string) throws ParseException {
        return dateFormatter.parse(string);
    }
    
    protected void setLastExecutedDate(Date date) {
        ((TextView) popupView.findViewById(R.id.executed_last_time_txt))
                .setText(formatLastExecutedDate(date));
    }
    
    protected String getActionName() {
        return getTextValue(R.id.action_name_txt);
    }
    
    @Nullable
    protected Date getLastExecutedDate() throws ValidationException {
        String value = getTextValue(R.id.executed_last_time_txt);
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        
        try {
            return parseLastExecutedDate(value);
        } catch (ParseException e) {
            System.out.println("!! Error: " + e.getMessage());
            throw new ValidationException("Can not parse date: " + value);
        }
    }
    
    @Nullable
    protected Integer getExecutionInterval() throws ValidationException {
        String value = getTextValue(R.id.days_number_txt);
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            System.out.println("!! Error: " + e.getMessage());
            throw new ValidationException("Can not parse execution interval: " + value);
        }
    }

    @NonNull
    private String getTextValue(int field) {
        TextView text = popupView.findViewById(field);
        return text.getText().toString().trim();
    }

    protected void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setNeutralButton("Close", (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public NewAction getAction() throws ValidationException {
        String actionName = getActionName();
        Integer executionInterval = getExecutionInterval();
        Date lastExecutedDate = getLastExecutedDate();

        
        return new NewAction(actionName, executionInterval, lastExecutedDate);
    }
    
    public void setReadOnly(boolean readOnly) {
            popupView.findViewById(R.id.action_name_txt).setEnabled(!readOnly);
            popupView.findViewById(R.id.days_number_txt).setEnabled(!readOnly);
            popupView.findViewById(R.id.executed_last_time_txt).setEnabled(!readOnly);
    }
}


