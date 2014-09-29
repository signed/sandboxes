package com.github.signed.integration.camel;

import java.util.HashMap;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.remote.SftpComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class SftpUpload {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("sftp", new SftpComponent(context));
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:trigger-download")
                        .to("http://scm:8081/nexus/content/repositories/releases/de/idos/operatingreserve/moccamote-application/1.3.0/moccamote-application-1.3.0.pom.sha1")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("processing");
                            }
                        })
                        .to("sftp://jenkins@localhost/standalone?password=jenkins");
            }
        });
        context.addRoutes(directSystemOut());


        context.start();
        ProducerTemplate template = context.createProducerTemplate();
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put(Exchange.HTTP_URI, "http://scm:8081/nexus/content/repositories/releases/de/idos/operatingreserve/moccamote-application/1.3.0/moccamote-application-1.3.0.pom");
        headers.put(Exchange.FILE_NAME, "the-file-name.pom");
        template.sendBodyAndHeaders("direct:trigger-download", null, headers);
        Thread.sleep(100000);
        context.stop();
    }

    private static RouteBuilder directSystemOut() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:system-out").to("stream:out");
            }
        };
    }

}
