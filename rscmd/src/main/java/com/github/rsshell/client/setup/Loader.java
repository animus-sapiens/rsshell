package com.github.rsshell.client.setup;

import com.github.rsshell.client.setup.log.LogBackConfigurer;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Spring-context loader for selected beans. */
public class Loader {

  /** Loads spring-beans. */
  public static void load() {
    ConfigurableApplicationContext context =
        new AnnotationConfigApplicationContext(
            Configurator.class, Config.class, LogBackConfigurer.class);

    Logger log = ESAPI.getLogger(Loader.class);
    log.info(Logger.EVENT_SUCCESS, "Application beans loaded.");
  }
}
