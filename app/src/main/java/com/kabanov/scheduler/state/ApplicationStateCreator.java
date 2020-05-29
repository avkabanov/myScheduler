package com.kabanov.scheduler.state;

import java.util.List;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.preferences.ProjectPreferences;
import com.kabanov.scheduler.state.converter.Converter;
import com.kabanov.scheduler.state.data.ActionDataState;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.data.SettingsPersistence;

public class ApplicationStateCreator {

    private ActionController actionController;
    private ProjectPreferences projectPreferences;
    private Converter converter;

    public ApplicationStateCreator(ActionController actionController,
                                   ProjectPreferences projectPreferences,
                                   Converter converter) {
        this.actionController = actionController;
        this.projectPreferences = projectPreferences;
        this.converter = converter;
    }

    public ApplicationState create() {
        List<ActionData> actions = actionController.getAllActions();
        List<ActionDataState> actionDataStateList = converter.toActionDataStateList(actions);
        SettingsPersistence settingsPersistence = toSettingsPersistence(projectPreferences);
        
        return new ApplicationState(actionDataStateList, settingsPersistence);
    }

    private SettingsPersistence toSettingsPersistence(ProjectPreferences projectPreferences) {
        SettingsPersistence result = new SettingsPersistence();
        result.setFishModeEnabled(projectPreferences.isFishModeEnabled());
        return result;
    }
}
