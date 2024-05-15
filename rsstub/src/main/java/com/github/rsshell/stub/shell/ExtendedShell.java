package com.github.rsshell.stub.shell;

import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Primary;
import org.springframework.shell.Input;
import org.springframework.shell.InputProvider;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import org.springframework.shell.command.CommandCatalog;
import org.springframework.shell.context.ShellContext;
import org.springframework.shell.exit.ExitCodeMappings;
import org.springframework.stereotype.Component;

/** Extended shell which takes in account special characters */
@Component
@Primary
public class ExtendedShell extends Shell {

  /**
   * Extended shell to handle evaluation.
   *
   * @param resultHandlerService result handler service
   * @param commandRegistry command registry
   * @param terminal terminal
   * @param shellContext shell context
   * @param exitCodeMappings exit code mappipngs
   */
  protected ExtendedShell(
      ResultHandlerService resultHandlerService,
      CommandCatalog commandRegistry,
      Terminal terminal,
      ShellContext shellContext,
      ExitCodeMappings exitCodeMappings) {
    super(resultHandlerService, commandRegistry, null, shellContext, exitCodeMappings);
  }

  @Override
  public Object evaluate(Input input) {
    return super.evaluate(input);
  }

  /**
   * Disables InteractiveShellRunner,i.e. shell standard I/O, i.e. hides shell's prompt when
   * application runs.
   *
   * @param inputProvider
   * @throws Exception
   */
  @Override
  public void run(InputProvider inputProvider) throws Exception {
    InputProvider dummyInputProvider =
        new InputProvider() {
          @Override
          public Input readInput() {
            Input nothingReturnInput =
                new Input() {
                  @Override
                  public String rawText() {
                    return "";
                  }
                };
            return nothingReturnInput;
          }
        };
    super.run(dummyInputProvider);
  }
}
