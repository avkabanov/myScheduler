package com.kabanov.scheduler.state.persistence;

import java.io.File;

import com.kabanov.scheduler.state.ActionsSaver;
import com.kabanov.scheduler.state.data.ApplicationState;
import com.kabanov.scheduler.state.file.FileWriter;
import com.kabanov.scheduler.state.xml.XmlParser;
import com.kabanov.scheduler.utils.Logger;

/**
 * @author Алексей
 * @date 14.04.2019
 */
public class XmlStatePersistence implements ActionsSaver {

    private static final Logger logger = Logger.getLogger(XmlStatePersistence.class.getName());

    private final XmlParser xmlParser = new XmlParser();
    private final FileWriter fileWriter;

    public XmlStatePersistence(File applicationDateDir, String activitiesStorageFilename) {
        fileWriter = new FileWriter(applicationDateDir, activitiesStorageFilename);
    }

    @Override
    public void save(ApplicationState applicationState) {
        try {
            String result = xmlParser.parse(applicationState);
            fileWriter.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public ApplicationState load() {
        try {
            String result = fileWriter.read();
            return xmlParser.format(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
