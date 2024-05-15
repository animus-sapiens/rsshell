package com.github.rsshell.stub;

import com.github.rsshell.stub.shell.ExtendedShell;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Input;

/** Handles RSocket with WebSocket shell-client requests. */
public class Handler {
  private ExtendedShell shell;

  public String executeCommand(String commandLine) {
    Input commandLineInput =
        new Input() {
          @Override
          public String rawText() {
            return commandLine;
          }
        };
    Object result = this.shell.evaluate(commandLineInput);
    String textOutput;
    if (result instanceof Exception) {
      textOutput =
          new AttributedString(
                  Config.OUTPUT.failed.name() + ": " + result,
                  AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
              .toAnsi();
    } else if (result instanceof AttributedString) {
      textOutput = ((AttributedString) result).toAnsi();
    } else {
      if (result != null) {
        textOutput = result.toString();
      } else {
        textOutput = Config.OUTPUT.success.name();
      }
    }
    // Text format fixing the client output on any OS.
    if (textOutput.contains("\n") && !textOutput.contains("\r\n")) {
      return textOutput.replaceAll("\n", "\r\n");
    } else {
      return textOutput;
    }
  }

  /**
   * Sets up stub-shell.
   *
   * @param shell
   */
  @Autowired
  public void setShell(ExtendedShell shell) {
    this.shell = shell;
  }
}
