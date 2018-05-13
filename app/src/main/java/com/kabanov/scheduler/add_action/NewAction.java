package com.kabanov.scheduler.add_action;

import java.util.Date;

public class NewAction {
    private String name;
    private int dates;
    private Date lastExecutedDate = new Date();

    public NewAction(String name, int dates) {
        this.name = name;
        this.dates = dates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDates() {
        return dates;
    }

    public void setDates(int dates) {
        this.dates = dates;
    }

    public void setLastExecutedDate(Date lastExecutedDate) {
        this.lastExecutedDate = lastExecutedDate;
    }

    public Date getLastExecutedDate() {
        return lastExecutedDate;
    }
}
