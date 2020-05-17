package com.kabanov.scheduler.state.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Алексей
 * @date 14.04.2019
 */
public class FileWriter {

    private final File targetFile;

    public FileWriter(File applicationDateDir, String activitiesStorageFilename) {
        targetFile = new File(applicationDateDir, activitiesStorageFilename);
    }

    public void write(String content) throws IOException {

        if (targetFile.exists()) {
            targetFile.delete();
        }
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(targetFile))) {
            writer.write(content);
        }
    }

    public String read() throws IOException {
        return new String(Files.readAllBytes(Paths.get(targetFile.toURI())));
    }
}
