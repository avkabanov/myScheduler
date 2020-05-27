package com.kabanov.scheduler.state.data;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.kabanov.scheduler.actions_table.ActionData;

@Root(name = "ApplicationState")
public class ApplicationState {

    @ElementList(name = "ActionDataStateList")
    private List<ActionDataState> actionDataStateList = new ArrayList<>();
    
    private SettingsPersistence settingsPersistence;

    public ApplicationState() {
    }

    public ApplicationState(List<ActionDataState> actionDataStateList, SettingsPersistence settingsPersistence) {
        this.actionDataStateList = actionDataStateList;
        this.settingsPersistence = settingsPersistence;
    }

    public List<ActionData> getActions() {
        List<ActionData> result = new ArrayList<>();
        for (ActionDataState actionDataState : actionDataStateList) {
            ActionData actionData = new ActionData(
                    actionDataState.getId(),
                    actionDataState.getName(),
                    actionDataState.getPeriodicityDays());
            actionData.setExecutedAt(actionDataState.getLastExecutionDate());
            result.add(actionData);

        }
        return result;
    }

    public SettingsPersistence getSettingsPersistence() {
        return settingsPersistence;
    }
}
