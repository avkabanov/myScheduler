package com.kabanov.scheduler.state.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ApplicationState")
public class ApplicationState implements Serializable {

    @Element(name = "version", required = false)
    private String version = "2";
    
    @ElementList(name = "ActionDataStateList")
    private List<ActionDataState> actionDataStateList = new ArrayList<>();

    @Element(name = "Settings", required = false)
    private SettingsPersistence settingsPersistence = new SettingsPersistence();

    public ApplicationState() {
    }

    public ApplicationState(List<ActionDataState> actionDataStateList, SettingsPersistence settingsPersistence) {
        this.actionDataStateList = actionDataStateList;
        this.settingsPersistence = settingsPersistence;
    }
    
    public SettingsPersistence getSettingsPersistence() {
        return settingsPersistence;
    }

    public void setActionDataStateList(List<ActionDataState> actionDataStateList) {
        this.actionDataStateList = actionDataStateList;
    }

    public List<ActionDataState> getActionDataStateList() {
        return actionDataStateList;
    }

    public void setSettingsPersistence(SettingsPersistence settingsPersistence) {
        this.settingsPersistence = settingsPersistence;
    }

    @Override
    public String toString() {
        return "ApplicationState{" +
                "actionDataStateList=" + actionDataStateList +
                ", settingsPersistence=" + settingsPersistence +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationState that = (ApplicationState) o;
        return Objects.equals(actionDataStateList, that.actionDataStateList) &&
                Objects.equals(settingsPersistence, that.settingsPersistence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionDataStateList, settingsPersistence);
    }
}
