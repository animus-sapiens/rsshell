package com.github.rsshell.client;

import ch.qos.logback.classic.Level;
import com.github.rsshell.client.helper.TestLogAndAssertionHelper;
import com.github.rsshell.client.setup.Config;
import com.github.rsshell.client.ws.RShellRequester;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class RemoteShellTest {

  Config config;
  private Scanner scanner;
  private RShellRequester rsRequester;
  private RemoteShell subject;

  @BeforeEach
  void setUp() throws Exception {
    config = new Config();
    ReflectionTestUtils.setField(config, "clientPrompt", "shell>");
    Config.setConfig(config);
    scanner = Mockito.mock(Scanner.class);
    rsRequester = Mockito.mock(RShellRequester.class);
    subject = new RemoteShell(config, rsRequester, scanner);
    TestLogAndAssertionHelper.setLogger(RemoteShell.class);
  }

  @AfterEach
  void close() {
    TestLogAndAssertionHelper.dispose(RemoteShell.class);
  }

  @Test
  public void open_shell_and_execute_spring_shell_command() throws Exception {
    Mockito.when(scanner.nextLine()).thenReturn("help").thenReturn("quit");
    Mockito.when(rsRequester.execute("help")).thenReturn("success");
    subject.open();
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client opened", Level.INFO.toInt(), 0));
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("success", Level.DEBUG.toInt(), 1));
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client exited", Level.INFO.toInt(), 2));
  }

  @Test
  public void open_shell_and_exit() {
    Mockito.when(scanner.nextLine()).thenReturn("exit");
    subject.open();
    Mockito.verify(rsRequester, Mockito.times(1)).close();
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client opened", Level.INFO.toInt(), 0));
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client exited", Level.INFO.toInt(), 1));
  }

  @Test
  public void open_shell_and_quit() {
    Mockito.when(scanner.nextLine()).thenReturn("quit");
    subject.open();
    Mockito.verify(rsRequester, Mockito.times(0)).close();
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client opened", Level.INFO.toInt(), 0));
    Assertions.assertTrue(
        TestLogAndAssertionHelper.assertLogContains("Client exited", Level.INFO.toInt(), 1));
  }

  @Test()
  public void open_shell_with_execute_command_failed() {
    PowerMockito.when(rsRequester.execute("help"))
        .thenThrow(new RuntimeException("failed connection"));
    Mockito.when(scanner.nextLine()).thenReturn("help");
    Exception thrown =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> subject.open(),
            "Expected subject.open() to throw exception");
    Assertions.assertTrue(thrown instanceof RuntimeException);
    Assertions.assertEquals(thrown.getMessage(), "failed connection");
  }
}
