package com.kabanov.scheduler.preferences;

import android.content.SharedPreferences;

public class ProjectPreferences {
    
    public static final String PREFERENCE_NAME = "prefs";

    private final SharedPreferences preferences;
    
    public ProjectPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setFishModeEnabled(boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Settings.IS_FISH_MODE_ENABLED.getAlias(), value);
        editor.apply();
    }
    
    public boolean isFishModeEnabled() {
        return preferences.getBoolean(Settings.IS_FISH_MODE_ENABLED.getAlias(), Settings.IS_FISH_MODE_ENABLED.getDefaultValue());
    }
    
    private enum Settings {
        IS_FISH_MODE_ENABLED("IS_FISH_MODE_ENABLED", false);

        private String alias;
        private boolean defaultValue;

        Settings(String alias, boolean defaultValue) {
            this.alias = alias;
            this.defaultValue = defaultValue;
        }

        public String getAlias() {
            return alias;
        }

        public boolean getDefaultValue() {
            return defaultValue;
        }
    }
}
