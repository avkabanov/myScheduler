package com.kabanov.scheduler.add_action;

import java.util.Date;

public class NewAction {
    private String name;
    private int periodicityDays;
    private Date lastExecutedDate = new Date();

    public NewAction(String name, int periodicityDays) {
        this.name = name;
        this.periodicityDays = periodicityDays;
    }

    public NewAction(String name, int periodicityDays, Date lastExecutedDate) {
        this.name = name;
        this.periodicityDays = periodicityDays;
        this.lastExecutedDate = lastExecutedDate;
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

    public void setLastExecutedDate(Date lastExecutedDate) {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastExecutedDate() {
        return lastExecutedDate;
    }

    @Override
    public String toString() {
        return "NewAction{" +
                "name='" + name + '\'' +
                ", periodicityDays=" + periodicityDays +
                ", lastExecutedDate=" + lastExecutedDate +
                '}';
    }
}
