package com.kabanov.scheduler.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ProjectPreferences {

    private final SharedPreferences preferences; 
    
    public ProjectPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public boolean isFishModeEnabled() {
        return preferences.getBoolean(Settings.IS_FISH_MODE_ENABLED.getAlias(), Settings.IS_FISH_MODE_ENABLED.getDefaultValue());
    }

    public void setFishModeEnabled(boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Settings.IS_FISH_MODE_ENABLED.getAlias(), value);
        editor.apply();
    }
    
    public enum Settings {
        IS_FISH_MODE_ENABLED("IS_FISH_MODE_ENABLED", true);

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
