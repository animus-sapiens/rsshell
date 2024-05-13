package com.github.rsshell.client.helper;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class TestLogAndAssertionHelper {
  private static Logger logger;
  private static ListAppender<ILoggingEvent> logWatcher;

  public static Logger setLogger(Class<?> clazz) {
    logWatcher = new ListAppender<>();
    logWatcher.start();
    logger = (Logger) LoggerFactory.getLogger(clazz);
    logger.addAppender(logWatcher);
    return logger;
  }

  public static boolean assertLogContains(String message, int logLevel, int logOrder) {
    boolean result = false;
    LoggingEvent event = (LoggingEvent) logWatcher.list.get(logOrder);
    if (event.getLevel().toInt() == logLevel && event.getMessage().contains(message)) {
      result = true;
    }
    return result;
  }

  public static void dispose(Class<?> clazz) {
    ((Logger) LoggerFactory.getLogger(clazz)).detachAndStopAllAppenders();
  }
}
