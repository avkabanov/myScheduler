package com.kabanov.scheduler.state.data;

import java.util.Objects;

import org.simpleframework.xml.Element;

import com.kabanov.scheduler.preferences.ProjectPreferences;

public class SettingsPersistence {

    @Element(name = "FishModeEnabled")
    private boolean fishModeEnabled = ProjectPreferences.Settings.IS_FISH_MODE_ENABLED.getDefaultValue();

    public SettingsPersistence() {
    }

    public boolean getFishModeEnabled() {
        return fishModeEnabled;
    }

    public void setFishModeEnabled(boolean fishModeEnabled) {
        this.fishModeEnabled = fishModeEnabled;
    }

    @Override
    public String toString() {
        return "SettingsPersistence{" +
                "fishModeEnabled=" + fishModeEnabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsPersistence that = (SettingsPersistence) o;
        return fishModeEnabled == that.fishModeEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishModeEnabled);
    }
}
