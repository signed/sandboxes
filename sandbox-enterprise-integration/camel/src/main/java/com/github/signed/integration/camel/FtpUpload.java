package com.github.signed.integration.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.remote.FtpComponent;
import org.apache.camel.component.stream.StreamComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FtpUpload {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("ftp", new FtpComponent(context));
        context.addComponent("stream", new StreamComponent());
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:/home/signed/dev/arbeitskopie/customers/build/files/?noop=true")
                        .log("Uploading file ${file:name}")
                        .multicast().parallelProcessing().to("ftp://localhost:10021?username=sally&password=secret&delay=2500", "ftp://localhost:10021?username=harry&password=secret&delay=2500")
                        .log("Uploaded file ${file:name} complete.");

                from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").log("GOTCHA").routeId("print text file").to("stream:out");
            }
        });

        context.start();
        Thread.sleep(100000);
        context.stop();
    }
}
