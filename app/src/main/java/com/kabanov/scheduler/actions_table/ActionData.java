package com.kabanov.scheduler.actions_table;

import com.kabanov.scheduler.utils.TimeUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActionData implements Serializable {
    private static final long serialVersionUID = 2348293740919238876L;

    private final String id;
    private String name;
    private int periodicityDays;
    private String comment;
    private List<Date> executionHistory;
    private Date nextExecutionDate;
    private Date lastExecutionDate;

    public ActionData(String id, String name, int periodicityDays) {
        this.id = id;
        this.name = name;
        this.periodicityDays = periodicityDays;
        lastExecutionDate = new Date();
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
        nextExecutionDate = TimeUtils.addDays(lastExecutionDate, periodicityDays);
    }

    public Date getNextExecutionDate() {
        return nextExecutionDate;
    }

    public String getId() {
        return id;
    }

    public void setExecutedAt(Date date) {
        lastExecutionDate = date;
        updateNextExecutionDate();
    }

    public boolean isOverdue() {
        return TimeUtils.cutWithDayAcc(nextExecutionDate) < TimeUtils.cutWithDayAcc(new Date());
    }

    public Date getLastExecutionDate() {
        return lastExecutionDate;
    }

    @Override
    public String toString() {
        return "ActionData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
