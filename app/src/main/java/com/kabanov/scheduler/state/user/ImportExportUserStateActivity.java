package com.kabanov.scheduler.state.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.kabanov.scheduler.R;
import com.kabanov.scheduler.state.file.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ImportExportUserStateActivity extends AppCompatActivity {

    private FileWriter fileWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileWriter = new FileWriter(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "export.txt");

        setContentView(R.layout.activity_user_state_selector);

        OpenMode openMode = getOpenMode();

        Button button = findViewById(R.id.button);
        EditText textEdit = findViewById(R.id.editText1);

        if (OpenMode.EXPORT == openMode) {
            button.setText("Export");
            button.setOnClickListener(v -> {
                try {
                    fileWriter.write(textEdit.getText().toString());
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error while exporting state", Toast.LENGTH_LONG).show();
                }
            });
            textEdit.setText(getIntent().getStringExtra(Extras.CONTENT.getAlias()));
        } else {
            button.setText("Import");
            Editable stateToImport = textEdit.getText();
            button.setOnClickListener(v -> {
                Intent data = new Intent();
                data.putExtra(Extras.CONTENT.getAlias(), stateToImport.toString());
                setResult(RESULT_OK, data);
                finish();
            });
        }
    }

    private OpenMode getOpenMode() {
        String openModeString = getIntent().getStringExtra(Extras.OPEN_MODE.getAlias());
        return OpenMode.findByAlias(openModeString);
    }

    enum OpenMode {
        EXPORT("export"),
        IMPORT("import");

        private String alias;

        OpenMode(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }

        public static OpenMode findByAlias(String alias) {
            return Arrays.stream(values()).filter(openMode -> openMode.getAlias().equals(alias)).findAny().orElse(null);
        }
    }

    public enum Extras {
        CONTENT("content"),
        OPEN_MODE("open_mode");

        private String alias;

        Extras(String alias) {
            this.alias = alias;
        }

        public String getAlias() {
            return alias;
        }
    }
}
