package com.kabanov.scheduler.action_details;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.common.base.Strings;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.data.NewAction;
import com.kabanov.scheduler.exceptions.ValidationException;
import com.kabanov.scheduler.utils.UIUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public abstract class BaseActionInfo extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();
    private EditText titleTxtEdt;
    private EditText periodicityDaysEdt;
    private EditText lastExecutionTimeEdt;

    private Optional<ActionData> initialAction;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_info);

        titleTxtEdt = findViewById(R.id.TitleTxt);
        lastExecutionTimeEdt = findViewById(R.id.LastExecutedTimeTxt);
        periodicityDaysEdt = findViewById(R.id.PeriodicityTxt);

        Button leftBtn = findViewById(R.id.edit_btn);
        Button rightBtn = findViewById(R.id.complete_btn);

        leftBtn.setOnClickListener(view -> setupButtonHandler(getLeftButtonConfiguration().getRequestedActions()));
        rightBtn.setOnClickListener(view -> setupButtonHandler(getRightButtonConfiguration().getRequestedActions()));

        setFieldsEditable(isFieldsEditable());
        setButtonView(leftBtn, getLeftButtonConfiguration());
        setButtonView(rightBtn, getRightButtonConfiguration());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getDialogTitle());

        initialAction = new Extras(getIntent()).getActionData();
        initialAction.ifPresent(this::fillActionData);
    }

    private static void setButtonView(Button button, ButtonConfiguration buttonConfiguration) {
        button.setText(buttonConfiguration.getTitle());
        button.setVisibility(buttonConfiguration.isVisible() ? View.VISIBLE : View.INVISIBLE);
    }

    private void setupButtonHandler(RequestedActions requestedActions) {
        try {
            Intent data = new Intent();
            Extras extras = new Extras(data);
            extras.setRequestedAction(requestedActions);
            enrichWithResult(extras);
            setResult(RESULT_OK, data);
            finish();
        } catch (ValidationException e) {
            showErrorDialog(e.getMessage());
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFieldsEditable(boolean allowEdit) {
        UIUtils.allowEdit(titleTxtEdt, allowEdit);
        UIUtils.allowEdit(periodicityDaysEdt, allowEdit);
        UIUtils.allowEdit(lastExecutionTimeEdt, false);
        
        if (allowEdit) {
            DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                lastExecutionTimeEdt.setText(formatLastExecutedDate(myCalendar.getTime()));
            };

            lastExecutionTimeEdt.setOnClickListener(v -> {
                new DatePickerDialog(this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            });
        }
    }

    private void fillActionData(ActionData actionData) {
        titleTxtEdt.setText(actionData.getName());
        periodicityDaysEdt.setText(String.valueOf(actionData.getPeriodicityDays()));
        lastExecutionTimeEdt.setText(formatLastExecutedDate(actionData.getLastExecutionDate()));
    }
    
    protected Optional<ActionData> getInitialAction() {
        return initialAction;
    }
    
    protected String getActionName() {
        return titleTxtEdt.getText().toString().trim();
    }

    protected Date getLastExecutedDate() throws ValidationException {
        String value = lastExecutionTimeEdt.getText().toString();
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        try {
            return parseLastExecutedDate(value);
        } catch (ParseException e) {
            throw new ValidationException("Can not parse date: " + value);
        }
    }

    private Date parseLastExecutedDate(String string) throws ParseException {
        return dateFormatter.parse(string);
    }

    protected Integer getExecutionInterval() throws ValidationException {
        String value = periodicityDaysEdt.getText().toString();
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new ValidationException("Can not parse execution interval: " + value);
        }
    }

    private String formatLastExecutedDate(Date date) {
        return dateFormatter.format(date);
    }

    protected void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setNeutralButton("Close", (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected abstract void enrichWithResult(Extras extras) throws ValidationException;

    protected abstract String getDialogTitle();

    protected abstract boolean isFieldsEditable();

    protected abstract ButtonConfiguration getLeftButtonConfiguration();

    protected abstract ButtonConfiguration getRightButtonConfiguration();

    public enum RequestedActions {
        CREATE,
        VIEW,
        EDIT, 
        COMPLETE,
        DELETE, 
        SAVE;
    }
    
    public static class Extras {
        Intent intent;

        public Extras(Intent intent) {
            this.intent = intent;
        }
        
        public Optional<ActionData> getActionData() {
            if (intent.getExtras() == null) {
                return Optional.empty();
            } 
            return Optional.ofNullable((ActionData)intent.getExtras().getSerializable("action_data"));
        }

        public void setActionData(ActionData actionData) {
            intent.putExtra("action_data", actionData );
        }

        public Optional<NewAction> getNewAction() {
            if (intent.getExtras() == null) {
                return Optional.empty();
            }
            return Optional.ofNullable((NewAction) intent.getExtras().getSerializable("new_action"));
        }

        public void setNewAction(NewAction newAction) {
            intent.putExtra("new_action", newAction );
        }
        
        public RequestedActions getRequestedAction() {
            return (RequestedActions) intent.getExtras().getSerializable("requested_action");     
        }

        public void setRequestedAction(RequestedActions requestedActions) {
            intent.putExtra("requested_action", requestedActions);
        }
    }
    
    static class ButtonConfiguration {
        private String title;
        private boolean visible;                                          
        private RequestedActions requestedActions;

        public ButtonConfiguration(String title, boolean visible, RequestedActions requestedActions) {
            this.title = title;
            this.visible = visible;
            this.requestedActions = requestedActions;
        }

        public String getTitle() {
            return title;
        }

        public boolean isVisible() {
            return visible;
        }

        public RequestedActions getRequestedActions() {
            return requestedActions;
        }
    }
}