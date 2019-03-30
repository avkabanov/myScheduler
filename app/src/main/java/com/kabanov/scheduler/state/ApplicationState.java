package com.kabanov.scheduler.state;

import java.util.List;

import com.kabanov.scheduler.actions_table.ActionData;

public class ApplicationState  {

    private List<ActionData> list;

    public ApplicationState() {
    }

    public ApplicationState(List<ActionData> list) {
        this.list = list;
    }

    public List<ActionData> getActions() {
        return list;
    }

    public void getActions(List<ActionData> list) {
        this.list = list;
    }

}
