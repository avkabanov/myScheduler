package com.kabanov.scheduler.state.data;

import java.util.Date;
import java.util.Objects;

import org.simpleframework.xml.Element;

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

    @Override
    public String toString() {
        return "ActionDataState{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", periodicityDays=" + periodicityDays +
                ", lastExecutionDate=" + lastExecutionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionDataState that = (ActionDataState) o;
        return periodicityDays == that.periodicityDays &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastExecutionDate, that.lastExecutionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, periodicityDays, lastExecutionDate);
    }
}
