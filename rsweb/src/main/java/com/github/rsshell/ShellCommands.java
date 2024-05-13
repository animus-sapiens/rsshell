package com.github.rsshell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Spring-Shell custom commands.
 *
 */
@ShellComponent
@Slf4j
public class ShellCommands {
  @ShellMethod(value = "System Information")
  public String systemInfo() {
    log.info("Get System Information");
    return SystemInformation.getSystemInfo();
  }
}
