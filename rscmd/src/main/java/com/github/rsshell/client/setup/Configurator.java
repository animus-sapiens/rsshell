package com.github.rsshell.client.setup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/** Used at spring-properties instantiation. */
@Configuration
public class Configurator {

  /**
   * Returns configurer supporting the property-placeholder element.
   * @return property-placeholder configurer
   */
  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource(Config.getAppConfig()));
    return propertySourcesPlaceholderConfigurer;
  }
}
