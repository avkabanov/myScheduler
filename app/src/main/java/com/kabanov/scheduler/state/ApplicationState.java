package com.kabanov.scheduler.state;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.kabanov.scheduler.actions_table.ActionData;

@XmlRootElement(name = "actions_list")
public class ApplicationState  {
    private static final long serialVersionUID = 2348251741919238876L;

    private List<ActionData> list;

    public ApplicationState() {
    }

    public ApplicationState(List<ActionData> list) {
        this.list = list;
    }

    public List<ActionData> getList() {
        return list;
    }

    public void setList(List<ActionData> list) {
        this.list = list;
    }

}
