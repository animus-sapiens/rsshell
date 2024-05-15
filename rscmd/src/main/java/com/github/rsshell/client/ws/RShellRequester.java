package com.github.rsshell.client.ws;

import com.github.rsshell.client.setup.Config;
import io.rsocket.metadata.WellKnownMimeType;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** RSocket client communicating over WS. */
public class RShellRequester {
  private static RSocketRequester rSocketRequester;

  public RShellRequester() {
    setUp();
  }

  /** Establishes connection. */
  private void setUp() {
    try {
      rSocketRequester =
          RSocketRequester.builder()
              .dataMimeType(MediaType.TEXT_PLAIN)
              .metadataMimeType(
                  MimeType.valueOf(
                      WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString()))
              .websocket(
                  URI.create(
                      Config.get().getWS_PROTOCOL()
                          + Config.get().getHost()
                          + ":"
                          + Config.get().getPort()
                          + Config.get().getRSOCKET_PATH()));
    } catch (RuntimeException ex) {
      System.out.println("Failed to connect." + ex.getMessage());
      System.exit(1);
    }
  }

  /**
   * Executes command.
   *
   * @param command
   * @return response
   */
  public String execute(String command) {
    String response;
    Mono<String> commandMono = Mono.just(command);
    Flux<String> commands = Flux.concat(commandMono);

    Flux<String> responseFlux =
        rSocketRequester
            .route(Config.get().getRSOCKET_ROUTE()) // message endpoint/route
            .data(commands)
            .retrieveFlux(String.class);

    List<String> responseList = responseFlux.collectList().block();
    response = responseList.stream().collect(Collectors.joining("\\r\\n"));
    return response;
  }

  /** Disposes connection and related resources. */
  public void close() {
    rSocketRequester.dispose();
  }
}
