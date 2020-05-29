package com.kabanov.scheduler.state.converter;

import java.util.ArrayList;
import java.util.List;

import com.kabanov.scheduler.actions_table.ActionData;
import com.kabanov.scheduler.state.data.ActionDataState;

public class Converter {

    public List<ActionDataState> toActionDataStateList(List<ActionData> actions) {
        List<ActionDataState> data = new ArrayList<>();
        for (ActionData actionData : actions) {
            data.add(toActionDataState(actionData));
        }
        return data;
    }

    public List<ActionData> toActionDataList(List<ActionDataState> list) {
        List<ActionData> result = new ArrayList<>();
        for (ActionDataState actionDataState : list) {
            result.add(toActionData(actionDataState));
        }
        return result;
    }

    public ActionDataState toActionDataState(ActionData actionData) {
        return new ActionDataState(
                actionData.getId(),
                actionData.getName(),
                actionData.getPeriodicityDays(),
                actionData.getLastExecutionDate());
    }
    
    public ActionData toActionData(ActionDataState actionDataState) {
        ActionData actionData = new ActionData(
                actionDataState.getId(),
                actionDataState.getName(),
                actionDataState.getPeriodicityDays());
        actionData.setExecutedAt(actionDataState.getLastExecutionDate());    
        return actionData;
    }
}
