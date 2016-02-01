package spark.examples.templateview;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import static spark.Spark.get;

public class FreeMarkerExample {

    public static void main(String args[]) {

        get("/hello", new TemplateViewRoute()
        {
            @Override
            public ModelAndView handle(Request request, Response response) throws Exception
            {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("message", "Hello FreeMarker World");

                // The hello.ftl file is located in directory:
                // src/test/resources/spark/examples/templateview/freemarker
                return new ModelAndView(attributes, "hello.ftl");
            }
        }, new FreeMarkerTemplateEngine());

    }

}