package com.kabanov.scheduler.actions_table;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActionData {

    private String id;
    private String name;
    private int periodicityDays;
    private String comment;
    private List<Date> executionHistory;
    private Date nextExecutionDate;

    public ActionData(String id, String name, int periodicityDays) {
        this.id = id;
        this.name = name;
        this.periodicityDays = periodicityDays;
        updateNextExecutionDate();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Date> getExecutionHistory() {
        return executionHistory;
    }

    public void setExecutionHistory(List<Date> executionHistory) {
        this.executionHistory = executionHistory;
    }

    private void updateNextExecutionDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, periodicityDays);
        nextExecutionDate = calendar.getTime();
    }

    public Date getNextExecutionDate() {
        return nextExecutionDate;
    }

    public String getId() {
        return id;
    }
}
