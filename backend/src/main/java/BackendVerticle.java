import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.Future;
import io.vertx.core.json.*;

public class BackendVerticle extends AbstractVerticle {
    private List<TextMessage> messages = new ArrayList<TextMessage>();

    @Override
    public void start(Future<Void> startFuture) {
        vertx
            .createHttpServer()
            .websocketHandler(this::websocketHandler)
              .listen(8080, result -> {
                  if (result.succeeded()) {
                    startFuture.complete();
                  } else {
                    startFuture.fail(result.cause());
                  }
            });
    }

    private void websocketHandler(ServerWebSocket webSocket) {
        // When the connection is first established, have the server write all previous messages.
        webSocket.writeTextMessage(Json.encodePrettily(messages));

        // Register this WebSocket connection as a consumer for the event bus address "messages".
        vertx.eventBus().consumer("messages", (Message<Object> message) -> {
            webSocket.writeTextMessage((String)message.body());
        });

        // Called each time the server receives a message.
        webSocket.handler(
            (Buffer data) -> {
                try {
                    // Decode the message and save it.
                    JsonObject textMessageJson = (JsonObject) Json.decodeValue(data);
                    TextMessage textMessage = new TextMessage(textMessageJson.getString("name"), textMessageJson.getString("message"));
                    messages.add(textMessage);

                    // Write the new messages to the event bus address "messages".
                    // The entire message history is broadcast each time. This allows clients after this connection to see old messages.
                    vertx.eventBus().publish("messages", Json.encodePrettily(messages));
                } catch (Exception e) {
                    System.err.println("Error occured processing message, shown below:");
                    e.printStackTrace();
                    throw e;
                }
            });
    }
}
