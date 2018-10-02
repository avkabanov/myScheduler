package com.kabanov.scheduler.utils;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * @author Алексей
 * @date 02.10.2018
 */
public class Log4jHelper {
  private final static LogConfigurator mLogConfigrator = new LogConfigurator();

  static {
    configureLog4j();
  }

  public static void configureLog4j() {
    //String fileName = Environment.getExternalStorageDirectory() + "/" + "log4j.log";
    String fileName = "log4j.log";
    String filePattern = "%d - [%c] - %p : %m%n";
    int maxBackupSize = 10;
    long maxFileSize = 1024 * 1024;

    configure( fileName, filePattern, maxBackupSize, maxFileSize );
  }

  private static void configure( String fileName, String filePattern, int maxBackupSize, long maxFileSize ) {
    mLogConfigrator.setFileName( fileName );
    mLogConfigrator.setMaxFileSize( maxFileSize );
    mLogConfigrator.setFilePattern(filePattern);
    mLogConfigrator.setMaxBackupSize(maxBackupSize);
    mLogConfigrator.setUseLogCatAppender(true);
    mLogConfigrator.configure();
  }
}