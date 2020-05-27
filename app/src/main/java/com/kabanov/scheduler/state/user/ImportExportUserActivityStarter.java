package com.kabanov.scheduler.state.user;

import com.kabanov.scheduler.intents.RequestCode;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.xml.XmlParser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ImportExportUserActivityStarter {

    private final XmlParser xmlParser = new XmlParser();
    private final AppCompatActivity activity;

    public ImportExportUserActivityStarter(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void exportUserState(ApplicationState applicationState) {
        String state;
        try {
            state = xmlParser.parse(applicationState);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error while exporting state", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(activity, ImportExportUserStateActivity.class);
        intent.putExtra(ImportExportUserStateActivity.Extras.CONTENT.getAlias(), state);
        intent.putExtra(ImportExportUserStateActivity.Extras.OPEN_MODE.getAlias(), ImportExportUserStateActivity.OpenMode.EXPORT.getAlias());
        activity.startActivity(intent);
    }

    public void requestImportUserState() {
        Intent intent = new Intent(activity, ImportExportUserStateActivity.class);
        intent.putExtra(ImportExportUserStateActivity.Extras.OPEN_MODE.getAlias(), ImportExportUserStateActivity.OpenMode.IMPORT.getAlias());

        activity.startActivityForResult(intent, RequestCode.IMPORT_USER_SETTINGS);
    }

}