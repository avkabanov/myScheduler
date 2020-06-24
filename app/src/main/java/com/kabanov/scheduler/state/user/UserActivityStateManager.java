package com.kabanov.scheduler.state.user;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kabanov.scheduler.state.ApplicationStateManager;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.xml.XmlParser;

public class UserActivityStateManager {
    private final AppCompatActivity activity;
    private final ImportExportUserActivityStarter importExportUserActivityStarter;
    private final XmlParser xmlParser = new XmlParser();
    private final ApplicationStateManager stateManager;

    public UserActivityStateManager(AppCompatActivity activity,
                                    ApplicationStateManager stateManager) {
        this.activity = activity;
        importExportUserActivityStarter = new ImportExportUserActivityStarter(activity);
        this.stateManager = stateManager;
    }

    public void exportUserState(ApplicationState applicationState) {
        importExportUserActivityStarter.exportUserState(applicationState);
    }

    public void requestImportUserState() {
        importExportUserActivityStarter.requestImportUserState();
    }

    public void onImportUserStateFinished(String state) {
        try {
            ApplicationState userState = xmlParser.format(state);
            stateManager.restoreState(userState);
        } catch (Exception e) {
            Toast.makeText(activity, "Import failed", Toast.LENGTH_LONG).show();
        }
    }
}
