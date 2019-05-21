package com.kabanov.scheduler.utils;

import java.util.logging.Level;

import android.util.Log;

/**
 * @author Алексей
 * @date 02.10.2018
 */
public class Logger {

    private final String tag;
    private final org.apache.log4j.Logger log4jLogger;
    private final java.util.logging.Logger javaUtilLogger;

    private static Logger instance;

    private Logger(String tag) {
        this.tag = tag;
        log4jLogger = org.apache.log4j.Logger.getLogger(tag);
        javaUtilLogger = java.util.logging.Logger.getLogger(tag);
    }

    public static Logger getLogger(String tag) {
        if (instance == null) {
            instance = new Logger(tag);
        }
        return instance;
    }

    public void info(String message) {
        log4jLogger.info("Log4j: " + message);
        Log.i(tag, "Log.level: " + message);
        javaUtilLogger.info("JavaUtilLogger: " + message);
    }

    public void debug(String message) {
        log4jLogger.debug("Log4j: " + message);
        Log.d(tag, "Log.level: " + message);
        javaUtilLogger.log(java.util.logging.Level.FINE, "JavaUtilLogger: " + message);
    }

    public void warn(String message) {
        log4jLogger.warn("Log4j: " + message);
        Log.w(tag, "Log.level: " + message);
        javaUtilLogger.log(Level.WARNING, "JavaUtilLogger: " + message);
    }

    public void error(String message) {
        log4jLogger.error("Log4j: " + message);
        Log.e(tag, "Log.level: " + message);
        javaUtilLogger.log(Level.SEVERE, "JavaUtilLogger: " + message);
    }
}
