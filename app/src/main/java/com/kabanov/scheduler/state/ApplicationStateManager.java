package com.kabanov.scheduler.state;

import android.content.Context;
import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.preferences.ProjectPreferences;
import com.kabanov.scheduler.state.converter.Converter;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.data.SettingsPersistence;
import com.kabanov.scheduler.state.inner.InnerActivityStateManager;
import com.kabanov.scheduler.utils.Logger;
import java.util.List;

public class ApplicationStateManager {
    private static final Logger logger = Logger.getLogger(ApplicationStateManager.class.getName());

    private InnerActivityStateManager innerActivityStateManager;
    private ActionController actionController;
    private ApplicationStateCreator applicationStateCreator;
    private ProjectPreferences projectPreferences;
    private Converter converter;

    public ApplicationStateManager(Context context, ActionController actionController,
                                   ApplicationStateCreator applicationStateCreator,
                                   Converter converter) {
        innerActivityStateManager = new InnerActivityStateManager(context);
        this.actionController = actionController;
        this.applicationStateCreator = applicationStateCreator;
        projectPreferences = new ProjectPreferences(context);
        this.converter = converter;
    }
    
    public void restoreInnerState() {
        ApplicationState state = innerActivityStateManager.loadInnerState();
        restoreState(state);
    }
    
    public void saveInnerState() {
        ApplicationState state = applicationStateCreator.create();
        saveState(state);
    }

    public void restoreState(ApplicationState state) {
        logger.debug("Restoring state");
        restoreActions(converter.toActionDataList(state.getActionDataStateList()));
        restoreSettings(state.getSettingsPersistence());
    }

    private void restoreSettings(SettingsPersistence settingsPersistence) {
        projectPreferences.setFishModeEnabled(settingsPersistence.getFishModeEnabled());
    }

    private void restoreActions(List<ActionData> actions) {
        logger.debug("Actions loaded " + actions.size());
        actionController.setActionsList(actions);
        logger.info("Actions added: " + actions.size());
    }

    public void saveState(ApplicationState applicationState) {
        innerActivityStateManager.saveInnerState(applicationState);
    }
}
