package com.kabanov.scheduler;

import java.util.List;

import org.acra.ACRA;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.data.StringFormat;

import com.kabanov.scheduler.action_details.dialogs.AddActionDialog;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenter;
import com.kabanov.scheduler.add_action.UpdateActionViewPresenterImpl;
import com.kabanov.scheduler.add_action.ValidationException;
import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.state.ActivityStateManager;
import com.kabanov.scheduler.state.ApplicationState;
import com.kabanov.scheduler.utils.Log4jHelper;
import com.kabanov.scheduler.utils.Logger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private ActivityStateManager activityStateManager;
    private ActionController actionController;
    public static MainActivity instance;
    {
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mainLayout = findViewById(R.id.content_main_layout);

        Log4jHelper.configureLog4j(getFilesDir());
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionController = new ActionsControllerImpl();
        
        UpdateActionViewPresenter updateActionViewPresenter = new UpdateActionViewPresenterImpl(actionController);
        ActionsTableController actionsTableController = new ActionsTableController(this, updateActionViewPresenter);
        actionController.setActionsTableController(actionsTableController);
        activityStateManager = new ActivityStateManager(getFilesDir(), this);
        mainLayout.addView(actionsTableController.getTableView());

        new NotificationController(this);
        Log.d("MainActivity","notification controller is set");

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {    
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            new AddActionDialog(MainActivity.this, action -> {
                try {
                    actionController.addActionRequest(action);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }).show();
        });
        initAcra();
        logger.debug("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.debug("OnStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sendLogs) {
            throw new RuntimeException("test exceotion 1");
        }

        if (id == R.id.action_export_actions) {
            activityStateManager.exportState(new ApplicationState(actionController.getAllActions()));
        }

        if (id == R.id.action_import_actions) {
            ApplicationState state = activityStateManager.importState();
            actionController.clearAll();
            for (ActionData actionData : state.getActions()) {
                try {
                    actionController.addActionRequest(actionData);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initAcra() {
        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this);
        builder.setBuildConfigClass(BuildConfig.class).setReportFormat(StringFormat.JSON);
        builder.setBuildConfigClass(BuildConfig.class).setApplicationLogFileLines(1000);
        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class).setEnabled(true);
        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class).setReportAsFile(false);
        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class).setMailTo("mrnuke@yandex.ru");
        ACRA.init(this.getApplication(), builder);
    }

    @Override
    protected void onPause() {
        ApplicationState applicationState = new ApplicationState(actionController.getAllActions());
        activityStateManager.saveState(applicationState);
        super.onPause();
    }

    @Override
    protected void onResume() {
        logger.debug("On Resume");
        
        logger.debug("Starting loading actions");
        List<ActionData> list = activityStateManager.loadState().getActions();
        logger.debug("Actions loaded " + list.size());

        actionController.clearAll();
        for (ActionData actionData : list) {
            try {
                actionController.addActionRequest(actionData);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }

        logger.info("Actions added: " + list.size());
        super.onResume();
    }

    public ActionController getActionController() {
        return actionController;
    }
}
