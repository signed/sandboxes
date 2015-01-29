package org.example.integration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.DocumentId;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jetty.connector.JettyConnectorProvider;

public class DocumentStorageDriver {

    public Response requestNotExistingDocument() {
        return execute(new Request() {
            @Override
            public String path() {
                return new DocumentId(Long.MAX_VALUE).asString();
            }

            @Override
            public Response invoke(Invocation.Builder invocation) {
                return invocation.get();
            }
        });
    }

    public static interface Request{
        String path();

        Response invoke(Invocation.Builder invocation);
    }

    public Response uploadDocumentWithContent(String content) {
        return execute(new Request() {
            @Override
            public String path() {
                return "";
            }

            @Override
            public Response invoke(Invocation.Builder invocation) {
                return invocation.post(Entity.entity(content, MediaType.APPLICATION_OCTET_STREAM_TYPE));
            }
        });
    }



    private Response execute(Request request) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new JettyConnectorProvider());
        clientConfig.register(new LoggingFilter());

        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target("http://localhost:8080/servlet-sample");

        Response response = request.invoke(target.path("storage/documents/" + request.path()).request());
        response.bufferEntity();
        return response;
    }
}
