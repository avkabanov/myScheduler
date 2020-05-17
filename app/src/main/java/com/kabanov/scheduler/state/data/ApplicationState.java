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

    public ApplicationState() {
    }

    public ApplicationState(List<ActionDataState> actionDataStateList) {
        this.actionDataStateList = actionDataStateList;
    }

    
    public static ApplicationState from(List<ActionData> actionDataList) {
        List<ActionDataState> data = new ArrayList<>();
        for (ActionData actionData : actionDataList) {
            data.add(ActionDataState.from(actionData));
        }
        return new ApplicationState(data);
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
}
