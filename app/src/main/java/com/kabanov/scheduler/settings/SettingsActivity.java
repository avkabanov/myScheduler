package com.kabanov.scheduler.settings;

import com.kabanov.scheduler.R;
import com.kabanov.scheduler.preferences.ProjectPreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox isFishModeEnabled;

    private final ProjectPreferences projectPreferences = new ProjectPreferences(
            getSharedPreferences(ProjectPreferences.PREFERENCE_NAME, MODE_PRIVATE));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        }

        isFishModeEnabled = findViewById(R.id.is_fish_mode_enabled);
        isFishModeEnabled.setOnCheckedChangeListener(
                (buttonView, isChecked) -> projectPreferences.setFishModeEnabled(isChecked));

        loadSettings();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadSettings() {
        setFishModeEnabled(projectPreferences.isFishModeEnabled());
    }

    private void setFishModeEnabled(boolean value) {
        isFishModeEnabled.setEnabled(value);
    }

}
