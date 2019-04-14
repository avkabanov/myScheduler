package com.kabanov.scheduler.state.backup_agent;

/**
 * @author Алексей
 * @date 30.03.2019
 */

import java.io.IOException;

import com.kabanov.scheduler.state.ActivityStateManager;
import com.kabanov.scheduler.utils.Logger;

import static com.kabanov.scheduler.state.ActivityStateManager.fileAccessLock;
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.app.backup.FileBackupHelper;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.os.ParcelFileDescriptor;

public class FilesBackupEngine extends BackupAgentHelper {
    private static final Logger logger = Logger.getLogger(FilesBackupEngine.class.getName());

    // A key to uniquely identify the set of backup data
    private static final String FILES_BACKUP_KEY = "my_actions";

    public static FilesBackupEngine instance;
    
    public FilesBackupEngine() {
        instance = this;
    }
    
    // Allocate a helper and add it to the backup agent
    @Override
    public void onCreate() {
        FileBackupHelper helper = new FileBackupHelper(this, ActivityStateManager.ACTIVITIES_STORAGE_FILENAME);
        addHelper(FILES_BACKUP_KEY, helper);
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
                         ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper performs backup
        synchronized (fileAccessLock) {
            logger.info("Data backed up");
            super.onBackup(oldState, data, newState);
        }
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
                          ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper restores the file
        synchronized (fileAccessLock) {
            logger.info("Data restored");
            super.onRestore(data, appVersionCode, newState);
        }
    }

    @Override
    public void onQuotaExceeded(long backupDataBytes, long quotaBytes) {
        logger.info("Quota Exceeded");
        super.onQuotaExceeded(backupDataBytes, quotaBytes);
    }

    public static void requestBackup(Context context) {
        BackupManager bm = new BackupManager(context);
        bm.dataChanged();
    }

    public static void requestRestore(Context context) {
        BackupManager bm = new BackupManager(context);
        int result = bm.requestRestore(new RestoreObserver() {
            @Override
            public void restoreStarting(int numPackages) {
                super.restoreStarting(numPackages);
            }

            @Override
            public void onUpdate(int nowBeingRestored, String currentPackage) {
                super.onUpdate(nowBeingRestored, currentPackage);
            }

            @Override
            public void restoreFinished(int error) {
                super.restoreFinished(error);
            }
        });
        System.out.println(result);
    }
}
