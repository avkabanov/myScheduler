package com.kabanov.scheduler.about;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.kabanov.scheduler.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fillVersionField();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
