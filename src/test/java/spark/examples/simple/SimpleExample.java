/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark.examples.simple;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * A simple example just showing some basic functionality
 *
 * @author Per Wendel
 */
public class SimpleExample {

    public static void main(String[] args) {

        //  port(5678); <- Uncomment this if you want spark to listen on a port different than 4567

        get("/hello", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Hello World!";
            }
        });

        post("/hello", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Hello World: " + request.body();
            }
        });

        get("/private", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                response.status(401);
                return "Go Away!!!";
            }
        });

        get("/users/:name", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "Selected user: " + request.params(":name");
            }
        });

        get("/news/:section", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                response.type("text/xml");
                return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
            }
        });

        get("/protected", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                halt(403, "I don't think so!!!");
                return null;
            }
        });

        get("/redirect", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                response.redirect("/news/world");
                return null;
            }
        });

        get("/", new Route()
        {
            @Override
            public Object handle(Request request, Response response) throws Exception
            {
                return "root";
            }
        });
    }
}
