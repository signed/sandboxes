package com.github.signed.sanbox.junit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.io.CharStreams;
import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;
import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import sun.tools.jstat.ColumnFormat;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.POST;

public class SampleTest {

    static final java.lang.String DEMO_OUTPUT = "demo-output";

    @Rule
    public PrintTestMethodNameRule printTestMethodNameRule = new PrintTestMethodNameRule();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        new TinyHttpServer().startServer();

        TransferObject transferObject = new TransferObject();
        transferObject.one = "one";
        transferObject.two = new Deep();
        transferObject.two.acme = "RoadRunner";


        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
        ResponseEntity<TransferObject> response = restTemplate.exchange("http://localhost:8000/test", POST, new HttpEntity<>(transferObject), TransferObject.class);

        assertThat(response.getBody().three, equalTo("three"));
    }

    public static class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

        private static final Logger logger = Logger.getLogger(DEMO_OUTPUT);

        @Override
        public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
            ClientHttpResponse response = execution.execute(request, body);

            return log(request, body, response);
        }

        private ClientHttpResponse log(final HttpRequest request, final byte[] body, final ClientHttpResponse response) throws IOException {
            ColumnFormatter<String> requestColumn = ColumnFormatter.text(Alignment.LEFT, 60);
            ColumnFormatter<String> responseColumn = ColumnFormatter.text(Alignment.LEFT, 60);
            String requestJson = prettyJson(new String(body, "UTF-8"));
            String responseJson = prettyJson(CharStreams.toString(new InputStreamReader(response.getBody(), "UTF-8")));


            String[] requestJsons = requestJson.split("\n");
            String[] responseJsons = responseJson.split("\n");
            Table.Builder builder = new Table.Builder("request", requestJsons, requestColumn);
            builder.addColumn("response", responseJsons, responseColumn);
            logger.info(request.getMethod().toString() + " " + request.getURI().toString());
            logger.info(builder.build());

            return response;
        }


        private String prettyJson(String ugly) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Object json = objectMapper.readValue(ugly, Object.class);
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static class TransferObject {
        public String one;
        public Deep two;
        public String three;
    }

    public static class Deep {
        public String acme;
        public String coyote;
    }

}
