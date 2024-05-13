package com.github.rsshell.client;

import com.github.rsshell.client.setup.Config;
import com.github.rsshell.client.setup.Loader;
import com.github.rsshell.client.ws.RShellRequester;

import java.io.IOException;
import java.util.Scanner;

/** Application entrypoint. */
public class Application {

  public static void main(String[] args) throws IOException {
    Loader.load();
    RemoteShell remoteShell =
        new RemoteShell(Config.get(), new RShellRequester(), new Scanner(System.in));
    remoteShell.open();
  }
}
