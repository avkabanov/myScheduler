package com.kabanov.scheduler.state;

import java.io.File;

import com.kabanov.scheduler.state.backup_agent.FilesBackupEngine;
import com.kabanov.scheduler.state.saver.BinaryFileSaver;

import android.content.Context;

public class ActivityStateManager {
    public static final String ACTIVITIES_STORAGE_FILENAME = "activities.dat";
    public static final Object fileAccessLock = new Object();
    private ActionsSaver saver;
    private Context context;

    public ActivityStateManager(File applicationDateDir, Context context) {
        saver = new BinaryFileSaver(applicationDateDir, ACTIVITIES_STORAGE_FILENAME);
        this.context = context;
    }

    public void saveState(ApplicationState applicationState) {
        synchronized (fileAccessLock) {
            saver.save(applicationState);
            FilesBackupEngine.requestBackup(context);
        }
    }

    public ApplicationState loadState() {
        ApplicationState state ;
        synchronized (fileAccessLock) {
            FilesBackupEngine.requestRestore(context);
            state =  saver.load();
        }
        saveState(state);
        return state;
    }
}
