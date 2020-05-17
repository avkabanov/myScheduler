package com.kabanov.scheduler.state;

import com.kabanov.scheduler.state.data.ApplicationState;

import android.support.annotation.Nullable;

/**
 * @author Алексей
 * @date 30.03.2019
 */
public interface ActionsSaver {
    
    void save(ApplicationState applicationState);
    
    @Nullable
    ApplicationState load();
}
