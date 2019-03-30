package com.kabanov.scheduler.state.backup_agent;

/**
 * @author Алексей
 * @date 30.03.2019
 */

import java.io.IOException;

import com.kabanov.scheduler.utils.Logger;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.os.ParcelFileDescriptor;

public class FilesBackupEngine extends BackupAgentHelper {
    private static final Logger logger = Logger.getLogger(FilesBackupEngine.class.getName());

    // The name of the file
    private  String actionsFile;

    // A key to uniquely identify the set of backup data
    private static final String FILES_BACKUP_KEY = "my_actions";
    private Object fileAccessLock;
    
    // Allocate a helper and add it to the backup agent
    @Override
    public void onCreate() {
        FileBackupHelper helper = new FileBackupHelper(this, actionsFile);
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
}
