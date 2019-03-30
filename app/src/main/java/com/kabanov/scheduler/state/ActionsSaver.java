package com.kabanov.scheduler.state;

/**
 * @author Алексей
 * @date 30.03.2019
 */
public interface ActionsSaver {
    
    void save(ApplicationState applicationState);
    
    ApplicationState load();
}
