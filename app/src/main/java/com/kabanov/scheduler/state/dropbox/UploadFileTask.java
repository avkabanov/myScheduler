package com.kabanov.scheduler.state.dropbox;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import android.os.AsyncTask;

/**
 * Async task to upload a file to a directory
 */
class UploadFileTask extends AsyncTask<String, Void, FileMetadata> {

    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public interface Callback {
        void onUploadComplete(FileMetadata result);
        void onError(Exception e);
    }

    UploadFileTask(DbxClientV2 dbxClient, Callback callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(FileMetadata result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else if (result == null) {
            mCallback.onError(null);
        } else {
            mCallback.onUploadComplete(result);
        }
    }

    @Override
    protected FileMetadata doInBackground(String... params) {
        String absolutePath = params[0];
        File localFile = new File(absolutePath);

        String remoteFolderPath = params[1];

        // Note - this is not ensuring the name is a valid dropbox file name
        String remoteFileName = localFile.getName();

        try (InputStream inputStream = new FileInputStream(localFile)) {
            return mDbxClient.files().uploadBuilder(remoteFolderPath + "/" + remoteFileName)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream);
        } catch (DbxException | IOException e) {
            mException = e;
        }

        return null;
    }
}