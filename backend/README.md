## How to run
1. Execute `mvn clean package` in the project root directory (requires [Maven](https://maven.apache.org/)). This command will create a JAR file in the **target** directory that can be executed.
2. Execute `java -jar target/goldman-sachs-instant-messenger-1.0-SNAPSHOT-fat.jar` to run the JAR file.

## How to test
1. Execute `mvn clean test` in the project root directory. Test results will be output to the console.

Alternatively, you can use the WebSocket Echo demo located at [http://www.websocket.org/echo.html](http://www.websocket.org/echo.html) and connect to the URL ws://localhost:8080/.
