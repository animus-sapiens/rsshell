package com.github.rsshell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** Application booting. */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  /**
   * Entry method
   *
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
