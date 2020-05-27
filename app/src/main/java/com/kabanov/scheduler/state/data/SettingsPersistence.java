package com.kabanov.scheduler.state.data;

import org.simpleframework.xml.Element;

@Element(name = "Settings")
public class SettingsPersistence {

    @Element(name = "FishModeEnabled")
    private Boolean fishModeEnabled;

    public SettingsPersistence() {
    }

    public Boolean getFishModeEnabled() {
        return fishModeEnabled;
    }

    public void setFishModeEnabled(Boolean fishModeEnabled) {
        this.fishModeEnabled = fishModeEnabled;
    }
}
