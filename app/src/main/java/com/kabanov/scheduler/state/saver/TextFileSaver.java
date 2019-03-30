package com.kabanov.scheduler.state.saver;

import com.kabanov.scheduler.state.ActionsSaver;
import com.kabanov.scheduler.state.ApplicationState;

/**
 * @author Алексей
 * @date 30.03.2019
 */
public class TextFileSaver implements ActionsSaver {
    @Override
    public void save(ApplicationState applicationState) {
        
    }

    @Override
    public ApplicationState load() {
        return null;
    }
}
