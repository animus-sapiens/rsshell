package com.github.rsshell.stub;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/** RSocket Controller */
@Controller
public class RSocketController extends Handler {

  /**
   * Entry of terminal messages/commands.
   *
   * @param commands
   * @return
   */
  @MessageMapping("terminal.shell")
  public Flux<String> shell(Flux<String> commands) {
    return commands.filter(data -> !data.trim().isEmpty()).map(this::executeCommand);
  }
}
