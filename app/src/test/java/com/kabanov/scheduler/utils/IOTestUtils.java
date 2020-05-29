package com.kabanov.scheduler.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOTestUtils {

    public static String readAllLinesFromResource(String name) throws URISyntaxException, IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(name);
        return new String(Files.readAllBytes(Paths.get(resource.toURI())));
    }
}
