package com.github.rsshell.stub;

import static org.mockito.ArgumentMatchers.any;

import com.github.rsshell.stub.shell.ExtendedShell;
import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.shell.Input;

@ExtendWith(MockitoExtension.class)
public class HandlerTest {
  @Mock
  ExtendedShell shell;
  @InjectMocks Handler subject;

  @Test
  public void spring_shell_command_success() throws Exception {
    Mockito.when(shell.evaluate(any(Input.class))).thenReturn("success");
    String response = subject.executeCommand("help");
    Assertions.assertEquals("success", response);
  }

  @Test
  public void spring_shell_command_not_found() throws Exception {
    Mockito.when(shell.evaluate(any(Input.class))).thenReturn(new Exception("command not found"));
    String response = subject.executeCommand("yadi-yadi-yada");
    Assertions.assertTrue(response.contains("failed"));
    Assertions.assertTrue(response.contains("command not found"));
  }

  @Test
  public void spring_shell_command_attribute() throws Exception {
    Mockito.when(shell.evaluate(any(Input.class))).thenReturn(new AttributedString("attribute"));
    String response = subject.executeCommand("one-attribute");
    Assertions.assertEquals("attribute", response);
  }

  @Test
  public void spring_shell_command_failed() {
    Mockito.when(shell.evaluate(any(Input.class)))
        .thenThrow(new RuntimeException("failed to evaluate"));
    Exception thrown =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> subject.executeCommand("help"),
            "Expected subject.open() to throw exception");
    //Assertions.assertTrue(thrown instanceof RuntimeException);
    Assertions.assertEquals(thrown.getMessage(), "failed to evaluate");
  }
}
