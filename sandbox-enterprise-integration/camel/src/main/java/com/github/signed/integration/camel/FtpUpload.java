package com.github.signed.integration.camel;

import java.util.HashMap;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.remote.FtpComponent;
import org.apache.camel.component.stream.StreamComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FtpUpload {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("ftp", new FtpComponent(context));
        context.addComponent("stream", new StreamComponent());
        //context.addRoutes(ftpMulticast());
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:trigger-download").to("http://scm:8081/nexus/content/repositories/releases/de/idos/operatingreserve/moccamote-application/1.3.0/moccamote-application-1.3.0.pom.sha1").to("stream:out");
            }
        });
        context.addRoutes(directSystemOut());


        context.start();
        ProducerTemplate template = context.createProducerTemplate();
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put(Exchange.HTTP_URI, "http://scm:8081/nexus/content/repositories/releases/de/idos/operatingreserve/moccamote-application/1.3.0/moccamote-application-1.3.0.pom");
        template.sendBodyAndHeaders("direct:trigger-download", null, headers);
        //template.sendBody("direct:trigger-download", null);
        //template.sendBody("direct:system-out", new URI("http://scm:8081/nexus/content/repositories/releases/de/idos/operatingreserve/moccamote-application/1.3.0/moccamote-application-1.3.0.pom"));
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

    private static RouteBuilder ftpMulticast() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:/home/signed/dev/arbeitskopie/customers/build/files/?noop=true")
                        .log("Uploading file ${file:name}")
                        .multicast().parallelProcessing().to("ftp://localhost:10021?username=sally&password=secret&delay=2500", "ftp://localhost:10021?username=harry&password=secret&delay=2500")
                        .log("Uploaded file ${file:name} complete.");

                from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").log("GOTCHA").routeId("print text file").to("stream:out");

            }
        };
    }
}
