package com.github.rsshell.client;

import com.github.rsshell.client.setup.Config;
import com.github.rsshell.client.ws.RShellRequester;
import java.util.Scanner;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;

/** Provides the interactive-shell. */
public class RemoteShell {
  private static Logger log = ESAPI.getLogger(RemoteShell.class);
  private final String clientPrompt;
  private RShellRequester rsRequester;
  private Scanner scanner;

  /**
   * Constructor
   * @param config
   * @param rsRequester
   * @param scanner
   */
  public RemoteShell(Config config, RShellRequester rsRequester, Scanner scanner) {

    this.clientPrompt = config.getClientPrompt();
    this.rsRequester = rsRequester;
    this.scanner = scanner;
  }

  /** Creates and handles shell. */
  public void open() {
    String command;
    log.info(Logger.EVENT_SUCCESS, "Client opened.");
    do {
      System.out.print(clientPrompt);
      command = scanner.nextLine();
      if (command.equals(Config.CommandsLocal.quit.name())
          || command.equals(Config.CommandsLocal.exit.name())) {
        if (command.equals(Config.CommandsLocal.exit.name())) {
          rsRequester.close();
        }
        log.info(Logger.EVENT_SUCCESS, "Client exited.");
        break;
      }
      String response = rsRequester.execute(command);
      log.debug(
          Logger.EVENT_SUCCESS,
          "Command executed [" + command + "] and responded with [" + response + "]");
      System.out.println(response);
    } while (true);
  }
}
