package com.kabanov.scheduler.data;

import java.io.Serializable;
import java.util.Date;

public class NewAction implements Serializable {
   
    // TODO generate UUID
    private String name;
    private Integer periodicityDays;
    private Date lastExecutedDate;

    public NewAction(String name, Integer periodicityDays, Date lastExecutedDate) {
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

    public Integer getPeriodicityDays() {
        return periodicityDays;
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
