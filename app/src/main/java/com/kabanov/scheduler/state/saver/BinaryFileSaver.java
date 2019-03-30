package com.kabanov.scheduler.state.saver;

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
import com.kabanov.scheduler.state.ActionsSaver;
import com.kabanov.scheduler.state.ApplicationState;

/**
 * @author Алексей
 * @date 30.03.2019
 */
public class BinaryFileSaver implements ActionsSaver {

    private final File applicationDateDir;
    private final String activitiesStorageFilename;

    public BinaryFileSaver(File applicationDateDir, String activitiesStorageFilename) {
        this.applicationDateDir = applicationDateDir;
        this.activitiesStorageFilename = activitiesStorageFilename;
    }

    @Override
    public void save(ApplicationState applicationState) {
        List<ActionData> allActions = applicationState.getActions();
        try {
            File file = new File(applicationDateDir, activitiesStorageFilename);
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
    }

    @Override
    public ApplicationState load() {
        List<ActionData> result = new ArrayList<>();

        try {
            File file = new File(applicationDateDir, activitiesStorageFilename);
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
        return new ApplicationState(result);
    }
}
