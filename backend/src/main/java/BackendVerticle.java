import java.util.ArrayList;
import java.util.List;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.Future;
import io.vertx.core.json.*;
import io.vertx.core.Vertx;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.ServerWebSocket;

public class BackendVerticle extends AbstractVerticle {
    private List<TextMessage> messages = new ArrayList<TextMessage>();

    @Override
    public void start(Future<Void> startFuture) {
        // // Set up the routes used by our application.
        // Router router = Router.router(vertx);
        // // Enable CORS for the frontend.
        // router.route().handler(
        //     CorsHandler.create("http://localhost:1234")  
        //         .allowedMethod(HttpMethod.GET)
        //         .allowedMethod(HttpMethod.POST)
        //         .allowedMethod(HttpMethod.OPTIONS)
        //         .allowCredentials(true)
        //         .allowedHeader("Access-Control-Allow-Method")
        //         .allowedHeader("Access-Control-Allow-Origin")
        //         .allowedHeader("Access-Control-Allow-Credentials")
        //         .allowedHeader("Content-Type")); 
        // // Configures a custom error handler (default behavior swallows errors).
        // router.errorHandler(500, routingContext -> {
        //     Object failure = routingContext.failure();
        //     if (failure instanceof Exception) {
        //         Exception exception = (Exception) failure;
        //         exception.printStackTrace();
        //         routingContext.response().end(exception.getMessage());
        //     } else {
        //         System.err.println(failure);
        //     }
        // });

        vertx
            .createHttpServer()
            .websocketHandler(this::websocketHandler)
            //.requestHandler(router)
              .listen(8080, result -> {
                  if (result.succeeded()) {
                    startFuture.complete();
                  } else {
                    startFuture.fail(result.cause());
                  }
            });
    }

    private void websocketHandler(ServerWebSocket webSocket) {
        // When the connection is first established, have the server write all messages.
        webSocket.writeTextMessage(Json.encodePrettily(messages));

        // Called each time the server receives a message.
        webSocket.handler(
            (Buffer data) -> {
                try {
                    // Decode the message and save it.
                    JsonObject textMessageJson = (JsonObject) Json.decodeValue(data);
                    System.out.println("Received " + textMessageJson.toString());
                    TextMessage textMessage = new TextMessage(textMessageJson.getString("name"), textMessageJson.getString("message"));
                    messages.add(textMessage);
                    webSocket.writeTextMessage(Json.encodePrettily(messages));
                } catch (Exception e) {
                    System.err.println("Error occured processing message, shown below:");
                    e.printStackTrace();
                    throw e;
                }
            });
    }
}
