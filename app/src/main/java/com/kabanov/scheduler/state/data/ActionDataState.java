package com.kabanov.scheduler.state.data;

import java.util.Date;

import org.simpleframework.xml.Element;

import com.kabanov.scheduler.actions_table.ActionData;

@Element(name = "ActionDataState")
public class ActionDataState {

    @Element(name = "id")
    private String id;
    @Element(name = "name")
    private String name;
    @Element(name = "periodicityDays")
    private int periodicityDays;
    @Element(name = "lastExecutionDate")
    private Date lastExecutionDate;

    public ActionDataState() {
    }

    public ActionDataState(String id, String name, int periodicityDays, Date lastExecutionDate) {
        this.id = id;
        this.name = name;
        this.periodicityDays = periodicityDays;
        this.lastExecutionDate = lastExecutionDate;
    }
    
    public static ActionDataState from(ActionData actionData) {
        return new ActionDataState(
                actionData.getId(), 
                actionData.getName(), 
                actionData.getPeriodicityDays(),
                actionData.getLastExecutionDate());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriodicityDays() {
        return periodicityDays;
    }

    public void setPeriodicityDays(int periodicityDays) {
        this.periodicityDays = periodicityDays;
    }

    public Date getLastExecutionDate() {
        return lastExecutionDate;
    }

    public void setLastExecutionDate(Date lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
    }
}
