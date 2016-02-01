package spark.examples.hello;

import static spark.Spark.get;
import static spark.Spark.secure;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * You'll need to provide a JKS keystore as arg 0 and its password as arg 1.
 */
public class HelloSecureWorld {
    public static void main(String[] args) {

        secure(args[0], args[1], null, null);
        get("/hello", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Hello Secure World!";
            }
        });

    }
}
