package com.github.signed.integration.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import com.github.signed.integration.camel.gui.CamelContextIgnition;
import com.github.signed.integration.camel.gui.swing.CamelEvaluationCenterSwing;

public class CamelEvaluationCenter {

    public static void main(String[] args) {
        CamelEvaluationCenter camelEvaluationCenter = new CamelEvaluationCenter();
        camelEvaluationCenter.run();

    }

    private void run() {
        CamelEvaluationCenterSwing gui = new CamelEvaluationCenterSwing();
        gui.constructApplicationFrame();
        CamelContext context = createCamelContext();

        addRoutesTo(context);
        new CamelContextIgnition(context, gui.startStop());
        gui.start();
    }

    private void addRoutesTo(CamelContext context) {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").routeId("welcome wagon").to("stream:out");
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CamelContext createCamelContext() {
        DefaultCamelContext context = new DefaultCamelContext();
        return context;
    }
}
