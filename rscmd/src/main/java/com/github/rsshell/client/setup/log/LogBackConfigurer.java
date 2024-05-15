package com.github.rsshell.client.setup.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.github.rsshell.client.setup.Config;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/** Log's dynamic-configuration. */
@Component
@DependsOn("Config")
public class LogBackConfigurer implements InitializingBean {
  private static final String LOGBACK_CONFIG_FILE = "slf4j.xml";
  @Autowired Config config;
  private Resource location;

  public void setLocation() {
    this.location = new ClassPathResource(LOGBACK_CONFIG_FILE);
  }

  /**
   * Invoked by the BeanFactory after all bean properties are set.
   *
   * @throws Exception
   */
  public void afterPropertiesSet() throws Exception {
    setLocation();
    LoggerContext context = null;
    JoranConfigurator configurator = new JoranConfigurator();
    context = (LoggerContext) LoggerFactory.getILoggerFactory();
    context.putProperty("logfile.name", config.getLoggingFileName());
    context.putProperty("logfile.path", config.getLoggingFilePath());
    context.putProperty("logfile.maxHistory", config.getLoggingFileMaxHistory());
    context.putProperty("logfile.totalSize", config.getLoggingFileTotalSize());
    context.putProperty("logging.pattern", config.getLoggingPattern());
    configurator.setContext(context);
    configurator.doConfigure(location.getInputStream());
    Logger log = ESAPI.getLogger(LogBackConfigurer.class);
    log.info(Logger.EVENT_SUCCESS, "Logger configured.");
  }
}
