package com.kabanov.scheduler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.kabanov.scheduler.about.AboutActivity;
import com.kabanov.scheduler.actions_table.ActionsTableController;
import com.kabanov.scheduler.actions_table.UpdateActionViewPresenter;
import com.kabanov.scheduler.actions_table.UpdateActionViewPresenterImpl;
import com.kabanov.scheduler.intents.RequestCode;
import com.kabanov.scheduler.logs.LogsSender;
import com.kabanov.scheduler.notification.NotificationController;
import com.kabanov.scheduler.preferences.ProjectPreferences;
import com.kabanov.scheduler.settings.SettingsActivity;
import com.kabanov.scheduler.state.ApplicationStateCreator;
import com.kabanov.scheduler.state.ApplicationStateManager;
import com.kabanov.scheduler.state.converter.Converter;
import com.kabanov.scheduler.state.user.ImportExportUserStateActivity;
import com.kabanov.scheduler.state.user.UserActivityStateManager;
import com.kabanov.scheduler.utils.Log4jHelper;
import com.kabanov.scheduler.utils.Logger;
import java.util.concurrent.atomic.AtomicBoolean;
import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.data.StringFormat;
import org.acra.ACRA;
import org.acra.BuildConfig;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.MailSenderConfigurationBuilder;
import org.acra.data.StringFormat;

public class MainActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private UserActivityStateManager userActivityStateManager;
    private ApplicationStateManager applicationStateManager;
    private ActionController actionController;
    private LogsSender logsSender = new LogsSender(this);
    private ApplicationStateCreator applicationStateCreator;
    private ProjectPreferences projectPreferences;
    public static MainActivity instance;
    public AtomicBoolean isDataLoaded = new AtomicBoolean(false);
    public Converter converter = new Converter();

    {
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log4jHelper.configureLog4j(getFilesDir());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionController = new ActionsControllerImpl(this);

        UpdateActionViewPresenter updateActionViewPresenter = new UpdateActionViewPresenterImpl(actionController);
        ActionsTableController actionsTableController = new ActionsTableController(this, updateActionViewPresenter);
        actionController.setActionsTableController(actionsTableController);
        projectPreferences = new ProjectPreferences(this);
        applicationStateCreator = new ApplicationStateCreator(actionController, projectPreferences, converter);
        applicationStateManager = new ApplicationStateManager(this, actionController, applicationStateCreator,
                converter);
        userActivityStateManager = new UserActivityStateManager(this, applicationStateManager);

        getResources().openRawResource(R.raw.version);
        new NotificationController(this);
        Log.d("MainActivity", "notification controller is set");

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            actionController.onCreateNewActionBtnClicked();
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
            logsSender.sendLogs();
        }

        if (id == R.id.action_export_actions) {
            userActivityStateManager.exportUserState(applicationStateCreator.create());
        }

        if (id == R.id.action_import_actions) {
            userActivityStateManager.requestImportUserState();
        }

        if (id == R.id.action_open_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        if (id == R.id.action_open_about) {
            startActivity(new Intent(this, AboutActivity.class));
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
        if (isDataChanged()) {
            applicationStateManager.saveInnerState();
        }
        super.onPause();
    }

    private boolean isDataChanged() {
        return true; // TODO  fix
    }

    @Override
    protected void onResume() {
        if (needToRestoreState()) {
            applicationStateManager.restoreInnerState();
            isDataLoaded.set(true);
        }
        super.onResume();
    }

    private boolean needToRestoreState() {
        return !isDataLoaded.get();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LogsSender.SEND_LOGS_REQUEST_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Granted.
                    logsSender.sendLogs();
                } else {
                    //Denied.
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case RequestCode.IMPORT_USER_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    userActivityStateManager.onImportUserStateFinished(
                            data.getStringExtra(ImportExportUserStateActivity.Extras.CONTENT.getAlias()));
                    applicationStateManager.saveInnerState();
                    Toast.makeText(this, actionController.getAllActions().size() + " actions imported successfully",
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
            case RequestCode.ACTION_UPDATE:
                MainActivity.this.actionController.onEvent(resultCode, data);
        }
    }

    public ActionController getActionController() {
        return actionController;
    }
}
