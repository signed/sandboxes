package com.github.signed.tryanderror.jersey;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyRestServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8182);

        ServletContextHandler handler = new ServletContextHandler();
        handler.setResourceBase("");
        handler.addEventListener(new GuiceConfig());
        handler.addFilter(GuiceFilter.class, "/*", null)        ;
        handler.addServlet(DefaultServlet.class, "/");
        handler.setContextPath("/buggy");

        WebAppContext webAppContext = createContext("/todo", "js-backbone-todo");
        WebAppContext inheritance = createContext("/inheritance", "js-inheritance");
        WebAppContext arrays = createContext("/arrays", "js-array");
        WebAppContext require = createContext("/require", "js-requirejs");
        WebAppContext requireFromScratch = createContext("/scratch", "js-requirejs-from-scratch");
        WebAppContext backbone = createContext("/backbone", "js-backbone");
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { handler, webAppContext, arrays, backbone, require, inheritance, requireFromScratch});
        server.setHandler(contexts);

        server.start();
        server.join();
    }

    private static WebAppContext createContext(String contextPath, String contentDirectory) {
        WebAppContext webAppContext = new WebAppContext("webapp", contextPath);
        webAppContext.setResourceBase("server/src/main/"+ contentDirectory);
        webAppContext.setDescriptor("WEB-INF/web.xml");
        return webAppContext;
    }
}