package com.kabanov.scheduler.state;

import android.support.annotation.Nullable;
import com.kabanov.scheduler.state.data.ApplicationState;

/**
 * @author Алексей
 * @date 30.03.2019
 */
public interface ActionsSaver {
    
    void save(ApplicationState applicationState);
    
    @Nullable
    ApplicationState load();
}
