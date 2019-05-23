package com.kabanov.scheduler.logs;

import java.io.File;
import java.io.IOException;

import com.kabanov.scheduler.MainActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * @author Алексей
 * @date 22.05.2019
 */
public class LogsSender {

    public static final int SEND_LOGS_REQUEST_PERMISSIONS = 103;
    private MainActivity activity;

    public LogsSender(MainActivity activity) {
        this.activity = activity;
    }

    public void sendLogs() {
        if (permissionGranted()) {
            Log.d("LogsSender", "Got READ_LOGS permissions");
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            doSendLogs();
        } else {
            Log.e("LOg4jHelper", "Don't have READ_LOGS permissions");
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, 
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    SEND_LOGS_REQUEST_PERMISSIONS);
        }
    }

    private void doSendLogs() {
        // save logcat in file
        File outputFile = new File(Environment.getExternalStorageDirectory(),
                "logcat.txt");
        try {
            Runtime.getRuntime().exec("logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //send file using email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // Set type to "email"
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"mrnuke@yandex.ru"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private boolean permissionGranted() {
        return ContextCompat
                .checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}
