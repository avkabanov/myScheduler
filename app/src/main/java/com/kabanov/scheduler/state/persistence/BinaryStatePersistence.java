package com.kabanov.scheduler.state.persistence;

import com.kabanov.scheduler.state.ActionsSaver;
import com.kabanov.scheduler.state.data.ApplicationState;

import android.content.Context;

public class BinaryStatePersistence {
    public static final String ACTIVITIES_STORAGE_FILENAME = "application-state.xml";
    public static final Object fileAccessLock = new Object();
    private final ActionsSaver innerState;
    
    public BinaryStatePersistence(Context context) {
        innerState = new XmlStatePersistence(context.getFilesDir(), ACTIVITIES_STORAGE_FILENAME);
    }

    public void saveInnerState(ApplicationState applicationState) {
        synchronized (fileAccessLock) {
            innerState.save(applicationState);
        }
    }

    public ApplicationState loadInnerState() {
        ApplicationState state;
        synchronized (fileAccessLock) {
            state = innerState.load();
        }
        if (state == null) {
            return new ApplicationState();    
        } else {
            return state;
        }
    }
}
