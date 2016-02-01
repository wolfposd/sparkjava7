package spark.webserver.websocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter;
import org.eclipse.jetty.websocket.server.pathmap.PathMappings;
import org.eclipse.jetty.websocket.server.pathmap.PathSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import spark.websocket.WebSocketTestHandler;

@RunWith(PowerMockRunner.class)
public class WebSocketServletContextHandlerFactoryTest {

    final String webSocketPath = "/websocket";
    private ServletContextHandler servletContextHandler;

    @Test
    public void testCreate_whenWebSocketHandlersIsNull_thenReturnNull() throws Exception {

        servletContextHandler = WebSocketServletContextHandlerFactory.create(null, WebSocketServletContextHandlerFactory.NO_TIMEOUT);

        assertNull("Should return null because no WebSocket Handlers were passed", servletContextHandler);

    }

    @Test
    public void testCreate_whenNoIdleTimeoutIsPresent() throws Exception {

//        Map<String, Class<?>> webSocketHandlers = new HashMap<>();
//
//        webSocketHandlers.put(webSocketPath, WebSocketTestHandler.class);
//
//        System.out.println("Creating:");
//        servletContextHandler = WebSocketServletContextHandlerFactory.create(null, WebSocketServletContextHandlerFactory.NO_TIMEOUT);
//
//        System.out.println("servletContextHandler: "+ servletContextHandler);
//        
//        WebSocketUpgradeFilter webSocketUpgradeFilter =
//                (WebSocketUpgradeFilter) servletContextHandler.getAttribute("org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter");
//
//        assertNotNull("Should return a WebSocketUpgradeFilter because we configured it to have one", webSocketUpgradeFilter);
//
//        PathMappings.MappedResource mappedResource = webSocketUpgradeFilter.getMappings().getMatch("/websocket");
//        WebSocketCreatorFactory.SparkWebSocketCreator sc = (WebSocketCreatorFactory.SparkWebSocketCreator) mappedResource.getResource();
//        PathSpec pathSpec = mappedResource.getPathSpec();
//
//        assertEquals("Should return the WebSocket path specified when contexst handler was created",
//                webSocketPath, pathSpec.getPathSpec());
//
//        assertTrue("Should return true because handler should be an instance of the one we passed when it was created",
//                sc.getHandler() instanceof WebSocketTestHandler);

    }

    @Test
    public void testCreate_whenTimeoutIsPresent() throws Exception {

        final long timeout = 1000;

        Map<String, Class<?>> webSocketHandlers = new HashMap<>();

        webSocketHandlers.put(webSocketPath, WebSocketTestHandler.class);

        servletContextHandler = WebSocketServletContextHandlerFactory.create(webSocketHandlers, timeout);

        WebSocketUpgradeFilter webSocketUpgradeFilter =
                (WebSocketUpgradeFilter) servletContextHandler.getAttribute("org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter");

        assertNotNull("Should return a WebSocketUpgradeFilter because we configured it to have one", webSocketUpgradeFilter);

        WebSocketServerFactory webSocketServerFactory = webSocketUpgradeFilter.getFactory();
        assertEquals("Timeout value should be the same as the timeout specified when context handler was created",
                timeout, webSocketServerFactory.getPolicy().getIdleTimeout());

        PathMappings.MappedResource mappedResource = webSocketUpgradeFilter.getMappings().getMatch("/websocket");
        WebSocketCreatorFactory.SparkWebSocketCreator sc = (WebSocketCreatorFactory.SparkWebSocketCreator) mappedResource.getResource();
        PathSpec pathSpec = mappedResource.getPathSpec();

        assertEquals("Should return the WebSocket path specified when context handler was created",
                webSocketPath, pathSpec.getPathSpec());

        assertTrue("Should return true because handler should be an instance of the one we passed when it was created",
                sc.getHandler() instanceof WebSocketTestHandler);
    }

    @Test
    @PrepareForTest(WebSocketServletContextHandlerFactory.class)
    public void testCreate_whenWebSocketContextHandlerCreationFails_thenThrowException() throws Exception {

        Map<String, Class<?>> webSocketHandlers = new HashMap<>();

        PowerMockito.whenNew(ServletContextHandler.class).withAnyArguments().thenThrow(new Exception(""));

        webSocketHandlers.put(webSocketPath, WebSocketTestHandler.class);

        servletContextHandler = WebSocketServletContextHandlerFactory.create(webSocketHandlers, WebSocketServletContextHandlerFactory.NO_TIMEOUT);

        assertNull("Should return null because Websocket context handler was not created", servletContextHandler);

    }
}