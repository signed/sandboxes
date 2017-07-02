package com.github.signed.sanbox.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class DemoRequestLog extends TestWatcher {

    private static final Logger logger = Logger.getLogger("demo-output");

    public static <T> ResponseEntity<T> logExchangeWith(RestTemplate restTemplate, Supplier<ResponseEntity<T>> call) {
        LoggingRequestInterceptor trace = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(trace);
        try {
            return call.get();
        } finally {
            restTemplate.getInterceptors().remove(trace);
        }
    }

    public static class RequestContext {
        public String name;
        public String json;
    }

    public static class Request {
        public String name;
        public HttpMethod method;
        public URI uri;
        public String requestJson;

        public List<RequestContext> contexts = new ArrayList<>();

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
        RequestContext context = new RequestContext();
        context.name = name;
        context.json = json;
        currentRequest.contexts.add(context);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void finished(Description description) {
        logger.info("Scenario: " + CaseFormat.LOWER_CAMEL.to(CaseFormat.SENTENCE, description.getMethodName()));
        requests.forEach(this::logRequest);
        logger.info("");
        requests.clear();
    }

    private void logRequest(Request request) {
        logger.info("");
        logger.info(request.method + " " + request.uri + " --> " + request.statusCode + " " + request.statusCode.getReasonPhrase());
        logger.info("");
        logger.info(jsonTable(request));
    }

    private Table jsonTable(Request request) {
        ColumnFormatter<String> requestColumn = ColumnFormatter.text(Alignment.LEFT, 55);
        ColumnFormatter<String> responseColumn = ColumnFormatter.text(Alignment.LEFT, 55);

        Table.Builder builder = new Table.Builder("request", toModel(request.requestJson), requestColumn);
        request.contexts.forEach(context -> {
            ColumnFormatter<String> contextColumn = ColumnFormatter.text(Alignment.LEFT, 55);
            builder.addColumn(context.name, toModel(context.json), contextColumn);
        });
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

    public enum CaseFormat {
        /**
         * Java variable naming convention, e.g., "lowerCamel".
         */
        LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
            @Override
            String normalizeWord(String word) {
                return firstCharOnlyToUpper(word);
            }
        },

        SENTENCE(CharMatcher.is(' '), " ") {
            @Override
            String normalizeWord(String word) {
                return Ascii.toLowerCase(word);
            }
        };

        private final CharMatcher wordBoundary;
        private final String wordSeparator;

        CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
            this.wordBoundary = wordBoundary;
            this.wordSeparator = wordSeparator;
        }

        /**
         * Converts the specified {@code String str} from this format to the specified {@code format}. A
         * "best effort" approach is taken; if {@code str} does not conform to the assumed format, then
         * the behavior of this method is undefined but we make a reasonable effort at converting anyway.
         */
        public final String to(CaseFormat format, String str) {
            checkNotNull(format);
            checkNotNull(str);
            return (format == this) ? str : convert(format, str);
        }

        /**
         * Enum values can override for performance reasons.
         */
        String convert(CaseFormat format, String s) {
            // deal with camel conversion
            StringBuilder out = null;
            int i = 0;
            int j = -1;
            while ((j = wordBoundary.indexIn(s, ++j)) != -1) {
                if (i == 0) {
                    // include some extra space for separators
                    out = new StringBuilder(s.length() + 4 * wordSeparator.length());
                    out.append(format.normalizeFirstWord(s.substring(i, j)));
                } else {
                    out.append(format.normalizeWord(s.substring(i, j)));
                }
                out.append(format.wordSeparator);
                i = j + wordSeparator.length();
            }
            return (i == 0)
                    ? format.normalizeFirstWord(s)
                    : out.append(format.normalizeWord(s.substring(i))).toString();
        }


        abstract String normalizeWord(String word);

        private String normalizeFirstWord(String word) {
            return (this == LOWER_CAMEL) ? Ascii.toLowerCase(word) : normalizeWord(word);
        }

        private static String firstCharOnlyToUpper(String word) {
            return (word.isEmpty())
                    ? word
                    : Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
        }
    }
}
