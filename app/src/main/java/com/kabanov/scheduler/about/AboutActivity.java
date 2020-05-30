package com.kabanov.scheduler.about;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.kabanov.scheduler.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fillVersionField();
    }
    
    private void fillVersionField() {
        TextView versionField = findViewById(R.id.appVersion);  
        String version = readVersion();
        if (version == null) {
            versionField.setText("Version: Undefined");
        } else {
            versionField.setText("Version: " + version);
        }
    }
    
    private String readVersion() {
        InputStream inputStream = getResources().openRawResource(R.raw.version);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.readLine();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
