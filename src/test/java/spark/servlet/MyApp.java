package spark.servlet;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.io.File;
import java.io.FileWriter;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

public class MyApp implements SparkApplication {

    public static final String EXTERNAL_FILE = "externalFileServlet.html";

    static File tmpExternalFile;

    @Override
    public synchronized void init() {
        try {
            externalStaticFileLocation(System.getProperty("java.io.tmpdir"));
            staticFileLocation("/public");

            tmpExternalFile = new File(System.getProperty("java.io.tmpdir"), EXTERNAL_FILE);
            FileWriter writer = new FileWriter(tmpExternalFile);
            writer.write("Content of external file");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        before("/protected/*", new Filter()
        {
            @Override
            public void handle(Request request, Response response) throws Exception
            {
                halt(401, "Go Away!");
            }
        });

        get("/hi", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Hello World!";
            }
        });

        get("/:param", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "echo: " + request.params(":param");
            }
        });

        get("/", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Hello Root!";
            }
        });

        post("/poster", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                String body = request.body();
                response.status(201); // created
                return "Body was: " + body;
            }
        });

        after("/hi", new Filter()
        {
            @Override
            public void handle(Request request, Response response) throws Exception
            {
                response.header("after", "foobar");
            }
        });

        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    @Override
    public void destroy()
    {
    }

}
