package com.kabanov.scheduler.action_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.utils.TimeUtils;
import com.kabanov.scheduler.utils.UIUtils;
import java.util.Optional;
import java.util.stream.Stream;

public class ActionInfo extends AppCompatActivity {

    private EditText title;
    private EditText periodicityDays;
    private EditText lastExecutionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_info);

        title = findViewById(R.id.TitleTxt);
        lastExecutionTime = findViewById(R.id.LastExecutedTimeTxt);
        periodicityDays = findViewById(R.id.PeriodicityTxt);

        Mode mode = Mode.fromAlias(getIntent().getExtras().getString(Extras.MODE.getAlias()));
        String title;
        switch (mode) {
            case VIEW:
                title = "View Action";
                setFieldsEditable(false);
                break;
            case CREATE:
                title = "Create Action";
                break;
            case MODIFY:
                title = "Modify Action";
                break;
            default:
                throw new IllegalStateException(mode.toString());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        ActionData actionData = (ActionData) getIntent().getExtras().getSerializable(Extras.ACTION.getAlias());
        if (actionData != null) {
            fillActionData(actionData);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFieldsEditable(boolean allowEdit) {
        UIUtils.allowEdit(title, allowEdit);
        UIUtils.allowEdit(periodicityDays, allowEdit);
        UIUtils.allowEdit(lastExecutionTime, allowEdit);
    }

    private void fillActionData(ActionData actionData) {
        title.setText(actionData.getName());
        periodicityDays.setText(String.valueOf(actionData.getPeriodicityDays()));
        lastExecutionTime.setText(TimeUtils.toUserTime(actionData.getLastExecutionDate().getTime()));
    }

    public enum Mode {
        CREATE("create"),
        VIEW("view"),
        MODIFY("modify");

        private String alias;

        Mode(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }

        static Mode fromAlias(String alias) {
            Optional<Mode> result = Stream.of(values()).filter(e -> e.getAlias().equals(alias)).findFirst();

            if (result.isPresent()) {
                return result.get();
            } else {
                throw new AssertionError(alias);
            }
        }
    }

    public enum Extras {
        MODE("mode"),
        ACTION("action");

        private String alias;

        Extras(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }

        static Extras fromAlias(String alias) {
            Optional<Extras> result = Stream.of(values()).filter(e -> e.getAlias().equals(alias)).findFirst();

            if (result.isPresent()) {
                return result.get();
            } else {
                throw new AssertionError(alias);
            }
        }
    }


}