package com.kabanov.scheduler.state;

import java.io.File;

import com.kabanov.scheduler.state.saver.BinaryFileSaver;

public class ActivityStateManager {
    public static final String ACTIVITIES_STORAGE_FILENAME = "activities.dat";
    private ActionsSaver saver;

    public ActivityStateManager(File applicationDateDir) {
        saver = new BinaryFileSaver(applicationDateDir, ACTIVITIES_STORAGE_FILENAME);
    }

    public void saveState(ApplicationState applicationState) {
        saver.save(applicationState);
    }

    public ApplicationState loadState() {
        return saver.load();
    }
}
