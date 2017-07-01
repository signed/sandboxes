package com.github.signed.sanbox.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

public class DemoRequestLog extends TestWatcher {

    private static final Logger logger = Logger.getLogger("demo-output");

    public static class RequestContext {
        public String name;
        public String json;
    }

    public static class Request {
        public String name;
        public HttpMethod method;
        public URI uri;
        public String requestJson;

        RequestContext context = new RequestContext();

        public HttpStatus statusCode;
        public String responseJson;

    }

    private static final List<Request> requests = Lists.newArrayList();
    private static Request currentRequest;

    public static void newRequest(String requestName) {
        currentRequest = new Request();
        currentRequest.name = requestName;
        requests.add(currentRequest);
    }

    public static void requestMethod(HttpMethod method) {
        currentRequest.method = method;
    }

    public static void requestUri(URI uri) {
        currentRequest.uri = uri;
    }

    public static void requestBody(String requestJson) {
        currentRequest.requestJson = requestJson;
    }

    public static void responseCode(HttpStatus statusCode) {
        currentRequest.statusCode = statusCode;
    }

    public static void responseBody(String responseJson) {
        currentRequest.responseJson = responseJson;
    }

    public static void requestContext(String name, String json) {
        currentRequest.context.name = name;
        currentRequest.context.json = json;
    }


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void finished(Description description) {
        logger.info("Scenario: " + CaseFormat.LOWER_CAMEL.to(CaseFormat.SENTENCE, description.getMethodName()));
        logger.info("");
        requests.forEach(this::logRequest);
    }

    private void logRequest(Request request) {
        logger.info(request.method + " " + request.uri + "\t\t" + request.statusCode + " " + request.statusCode.getReasonPhrase());
        logger.info("");
        logger.info(jsonTable(request));
    }

    private Table jsonTable(Request request) {
        ColumnFormatter<String> requestColumn = ColumnFormatter.text(Alignment.LEFT, 55);
        ColumnFormatter<String> contextColumn = ColumnFormatter.text(Alignment.LEFT, 55);
        ColumnFormatter<String> responseColumn = ColumnFormatter.text(Alignment.LEFT, 55);

        Table.Builder builder = new Table.Builder("request", toModel(request.requestJson), requestColumn);
        builder.addColumn(request.context.name, toModel(request.context.json), contextColumn);
        builder.addColumn("response", toModel(request.responseJson), responseColumn);
        return builder.build();
    }

    private String[] toModel(String json) {
        String[] rows = prettyJson(json).split("\n");
        for (int i = 0; i < rows.length; i++) {
            rows[i] = "   " + rows[i];
        }
        return rows;
    }

    private String prettyJson(String ugly) {
        try {
            Object json = objectMapper.readValue(ugly, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
            ClientHttpResponse response = execution.execute(request, body);
            return log(request, body, response);
        }

        private ClientHttpResponse log(final HttpRequest request, final byte[] body, final ClientHttpResponse response) throws IOException {
            String requestJson = new String(body, "UTF-8");
            String responseJson = CharStreams.toString(new InputStreamReader(response.getBody(), "UTF-8"));

            DemoRequestLog.requestMethod(request.getMethod());
            DemoRequestLog.requestUri(request.getURI());
            DemoRequestLog.requestBody(requestJson);
            DemoRequestLog.responseBody(responseJson);
            DemoRequestLog.responseCode(response.getStatusCode());
            return response;
        }

    }
}
