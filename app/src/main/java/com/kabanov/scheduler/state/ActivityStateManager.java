package com.kabanov.scheduler.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.kabanov.scheduler.actions_table.ActionData;

public class ActivityStateManager {
    public static final String ACTIVITIES_STORAGE_FILENAME = "activities.dat";
    private File applicationDateDir;

    public ActivityStateManager(File applicationDateDir) {
        this.applicationDateDir = applicationDateDir;
    }

    public void saveActions(List<ActionData> allActions) {
        try {
            File file = new File(applicationDateDir, ACTIVITIES_STORAGE_FILENAME);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            // size
            o.writeInt(allActions.size());
            // actions
            for (ActionData action : allActions) {
                o.writeObject(action);
            }
            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        
        ///

        //DropboxActions dropboxActions = new DropboxActions("Apps/MyScheduler");
        //dropboxActions.performWithPermissions(DropboxActions.FileAction.UPLOAD, new File(applicationDateDir, ACTIVITIES_STORAGE_FILENAME).getAbsolutePath());
    }

    public List<ActionData> loadActions() {
        List<ActionData> result = new ArrayList<>();

        try {
            File file = new File(applicationDateDir, ACTIVITIES_STORAGE_FILENAME);
            if (file.exists()) {
                FileInputStream fi = new FileInputStream(file);
                ObjectInputStream oi = new ObjectInputStream(fi);

                // size
                int size = oi.readInt();
                for (int i = 0; i < size; i++) {
                    ActionData actionData = (ActionData) oi.readObject();
                    System.out.println(actionData.toString());
                    result.add(actionData);
                }
            } else {
                System.out.println("File not found. Skipping");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
