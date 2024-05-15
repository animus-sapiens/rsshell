package com.github.rsshell.stub;

import java.nio.charset.StandardCharsets;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** WebSocket request handler. */
public class WSHandler extends Handler implements WebSocketHandler {
  /**
   * Fetches and dispatches response per session.
   *
   * @param session the session to handle
   * @return terminal output
   */
  @Override
  public Mono<Void> handle(WebSocketSession session) {
    Flux<WebSocketMessage> output =
        session
            .receive()
            .map(wsMessage -> wsMessage.getPayloadAsText(StandardCharsets.UTF_8))
            .filter(payloadText -> !payloadText.trim().isEmpty())
            .map(this::executeCommand)
            .map(session::textMessage);

    return session.send(output);
  }
}
