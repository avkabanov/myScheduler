package com.kabanov.scheduler.state.saver;

import java.io.File;
import java.io.IOException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.kabanov.scheduler.state.ActionsSaver;
import com.kabanov.scheduler.state.ApplicationState;

/**
 * @author Алексей
 * @date 14.04.2019
 */
public class XmlFileSaver implements ActionsSaver {

    private final File applicationDateDir;
    private final String activitiesStorageFilename;

    public XmlFileSaver(File applicationDateDir, String activitiesStorageFilename) {
        this.applicationDateDir = applicationDateDir;
        this.activitiesStorageFilename = activitiesStorageFilename;
    }

    @Override
    public void save(ApplicationState applicationState) {
        File file = new File(applicationDateDir, activitiesStorageFilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Serializer serializer = new Persister();

        try {
            serializer.write(applicationState, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ApplicationState load() {
        File xmlFile = new File(activitiesStorageFilename);

        Serializer serializer = new Persister();

        ApplicationState example = null;

        try {
            example = serializer.read(ApplicationState.class, xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return example;
    }
}
