package spark.webserver.websocket;

import static spark.Spark.get;

/**
 * Created by Per Wendel on 2015-05-18.
 */
public class WebSocketSpark {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }

}
