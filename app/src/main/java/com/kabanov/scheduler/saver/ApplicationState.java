package com.kabanov.scheduler.saver;

import com.kabanov.scheduler.actions_table.ActionData;

import java.util.Date;
import java.util.List;

public class ApplicationState  {
    private static final long serialVersionUID = 2348251741919238876L;

    private List<ActionData> list;
    private Date nextNotificationTime;

    public ApplicationState() {
    }

    public ApplicationState(List<ActionData> list, Date nextNotificationTime) {
        this.list = list;
        this.nextNotificationTime = nextNotificationTime;
    }

    public List<ActionData> getList() {
        return list;
    }

    public void setList(List<ActionData> list) {
        this.list = list;
    }

    public Date getNextNotificationTime() {
        return nextNotificationTime;
    }

    public void setNextNotificationTime(Date nextNotificationTime) {
        this.nextNotificationTime = nextNotificationTime;
    }
}
