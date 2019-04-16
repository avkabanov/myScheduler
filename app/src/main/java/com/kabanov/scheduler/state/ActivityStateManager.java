package com.kabanov.scheduler.state;

import java.io.File;

import com.kabanov.scheduler.state.saver.BinaryFileSaver;
import com.kabanov.scheduler.state.saver.XmlFileSaver;

import android.content.Context;
import android.os.Environment;

public class ActivityStateManager {
    public static final String ACTIVITIES_STORAGE_FILENAME = "activities.dat";
    public static final Object fileAccessLock = new Object();
    private ActionsSaver saver;
    private ActionsSaver exporter;
    private Context context;

    public ActivityStateManager(File applicationDateDir, Context context) {
        saver = new BinaryFileSaver(applicationDateDir, ACTIVITIES_STORAGE_FILENAME);
        exporter = new XmlFileSaver(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "export.txt");
        this.context = context;
    }

    public void saveState(ApplicationState applicationState) {
        synchronized (fileAccessLock) {
            saver.save(applicationState);
        }
    }

    public ApplicationState loadState() {
        ApplicationState state ;
        synchronized (fileAccessLock) {
            state =  saver.load();
        }
        return state;
    }
    
    public void exportState(ApplicationState applicationState) {
        synchronized (fileAccessLock) {
            exporter.save(applicationState);
        }
    }
    
    public ApplicationState importState() {
        ApplicationState state;
        synchronized (fileAccessLock) {
            state = exporter.load();
        }
        saveState(state);
        return state;
    }
}
