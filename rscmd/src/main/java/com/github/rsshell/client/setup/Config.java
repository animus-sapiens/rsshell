package com.github.rsshell.client.setup;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/** Final and environment-dependent configurations. */
@Getter
@Configuration("Config")
public class Config {
  private static final String APP_PROPERTIES = "application.properties";
  private static Config config;
  private final String RSOCKET_PATH = "/rsocket";
  private final String RSOCKET_ROUTE = "terminal.shell";
  private final String WS_PROTOCOL = "ws://";

  @Value("${rsocket.client.server.port}")
  private String port;

  @Value("${rsocket.client.server.host}")
  private String host;

  @Value("${logging.file.name}")
  private String loggingFileName;

  @Value("${logging.file.path}")
  private String loggingFilePath;

  @Value("${logging.file.maxHistory}")
  private String loggingFileMaxHistory;

  @Value("${logging.file.totalSize}")
  private String loggingFileTotalSize;

  @Value("${logging.pattern}")
  private String loggingPattern;

  @Value("${com.github.rsshell.client.prompt}")
  private String clientPrompt;

  public static Config get() {
    return config;
  }

  public String getClientPrompt() {
    return clientPrompt;
  }

  public static void setConfig(Config theConfig) {
    config = theConfig;
  }

  public static String getAppConfig() {
    return APP_PROPERTIES;
  }

  /**
   * Sets config after instantiation.
   */
  @PostConstruct
  private void postConstruct() {
    setConfig(this);
  }

  public enum
      CommandsLocal { // Only local commands. The Remote commands are executed/validated at SS.
    quit,
    exit
  }
}
