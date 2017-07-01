package com.github.signed.sanbox.junit;

import com.google.common.io.ByteStreams;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;

import static java.util.stream.Collectors.joining;

public class TinyHttpServer {

    private static Logger logger = Logger.getLogger(TinyHttpServer.class);

    public static void main(String[] args) throws IOException {
        new TinyHttpServer().startServer();
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.start();
    }

    private static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            soutRequest(exchange);
            sendResponse(exchange);
        }

        private void soutRequest(HttpExchange exchange) throws IOException {
            Headers requestHeaders = exchange.getRequestHeaders();
            requestHeaders.forEach((key, value) -> logger.info(key + ": " + value.stream().collect(joining(","))));

            byte[] bytes = ByteStreams.toByteArray(exchange.getRequestBody());
            String jsonBody = new String(bytes, "UTF-8");

            logger.info("request body \n" + jsonBody);
        }

        private void sendResponse(HttpExchange exchange) throws IOException {
            String response = "{\n" +
                    "  \"three\": \"three\"\n" +
                    "}";
            exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
