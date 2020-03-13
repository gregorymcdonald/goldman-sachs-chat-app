import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.*;
import io.vertx.core.Vertx;
import io.vertx.core.http.WebSocket;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class BackendVerticleTest {
    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(BackendVerticle.class.getName(), context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void ConnectToWebSocket_WritesExistingMessages(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().websocket(8080, "localhost", "",
            (WebSocket webSocket) -> {
                webSocket.handler(
                    (Buffer buffer) -> {
                        try {
                            JsonArray messages = (JsonArray) Json.decodeValue(buffer);
                            context.assertTrue(messages.isEmpty());
                        } catch(Exception e) {
                            context.fail(e);
                        } finally {
                            async.complete();
                        }
                    }
                );
        });
    }

    @Test
    public void WriteMessageToWebSocket_ReturnsMessage(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().websocket(8080, "localhost", "",
            (WebSocket webSocket) -> {
                TextMessage textMessage = new TextMessage("Gregory", "Hello World!");               
                webSocket.writeTextMessage(Json.encodePrettily(textMessage));
                webSocket.handler(
                    (Buffer buffer) -> {
                        try {
                            JsonArray messages = (JsonArray) Json.decodeValue(buffer);
                            if (!messages.isEmpty()) {
                                JsonObject firstMessage = messages.getJsonObject(0);
                                context.assertEquals(textMessage.getName(), firstMessage.getString("name"));
                                context.assertEquals(textMessage.getMessage(), firstMessage.getString("message"));
                            }
                        } catch(Exception e) {
                            context.fail(e);
                        } finally {
                            async.complete();
                        }
                    }
                );
        });
    }
}