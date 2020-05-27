package com.kabanov.scheduler.state;

import java.util.ArrayList;
import java.util.List;

import com.kabanov.scheduler.ActionController;
import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.preferences.ProjectPreferences;
import com.kabanov.scheduler.state.data.ActionDataState;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.data.SettingsPersistence;

public class ApplicationStateCreator {

    private ActionController actionController;
    private ProjectPreferences projectPreferences;

    public ApplicationStateCreator(ActionController actionController,
                                   ProjectPreferences projectPreferences) {
        this.actionController = actionController;
        this.projectPreferences = projectPreferences;
    }

    public ApplicationState create() {
        List<ActionData> actions = actionController.getAllActions();
        List<ActionDataState> actionDataStateList = toActionDataState(actions);
        SettingsPersistence settingsPersistence = toSettingsPersistence(projectPreferences);
        
        return new ApplicationState(actionDataStateList, settingsPersistence);
    }

    private SettingsPersistence toSettingsPersistence(ProjectPreferences projectPreferences) {
        SettingsPersistence result = new SettingsPersistence();
        result.setFishModeEnabled(projectPreferences.isFishModeEnabled());
        return result;
    }

    private List<ActionDataState> toActionDataState(List<ActionData> actions) {
        List<ActionDataState> data = new ArrayList<>();
        for (ActionData actionData : actions) {
            data.add(ActionDataState.from(actionData));
        }
        return data;
    }
}
