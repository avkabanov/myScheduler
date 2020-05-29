package com.kabanov.scheduler.state.data.legacy;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.kabanov.scheduler.state.data.ActionDataState;
import com.kabanov.scheduler.state.data.ApplicationState;

@Root(name = "applicationState")
public class ApplicationState00 {
    
    private com.kabanov.scheduler.state.converter.Converter converter = new com.kabanov.scheduler.state.converter.Converter();

    @ElementList(name = "list")
    private List<ActionData> list;

    public ApplicationState00() {
    }

    public ApplicationState00(List<ActionData> list) {
        this.list = list;
    }

    public List<ActionData> getActions() {
        return list;
    }

    public void getActions(List<ActionData> list) {
        this.list = list;
    }

    public void setList(List<ActionData> list) {
        this.list = list;
    }
    
    public static class Converter {
        
        public ApplicationState convert(ApplicationState00 state00) {
            
            ApplicationState result = new ApplicationState();

            List<ActionDataState> actionDSList = new ArrayList<>();

            for (ActionData actionData : state00.list) {
                actionDSList.add(toActionDataState(actionData));
            }
            result.setActionDataStateList(actionDSList);
            return result;
        }
        
        private ActionDataState toActionDataState(ActionData actionData) {

            ActionDataState result = new ActionDataState();
            result.setId(actionData.getId());           
            result.setName(actionData.getName());           
            result.setLastExecutionDate(actionData.getLastExecutionDate());           
            result.setPeriodicityDays(actionData.getPeriodicityDays());           
            return result;
        }
    }
}
