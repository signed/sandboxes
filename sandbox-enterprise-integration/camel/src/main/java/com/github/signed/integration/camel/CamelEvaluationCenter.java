package com.github.signed.integration.camel;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import com.github.signed.integration.camel.gui.CamelContextIgnition;
import com.github.signed.integration.camel.gui.UserCommand;
import com.github.signed.integration.camel.gui.swing.CamelEvaluationCenterSwing;

public class CamelEvaluationCenter {

    public static void main(String[] args) {
        CamelEvaluationCenter camelEvaluationCenter = new CamelEvaluationCenter();
        camelEvaluationCenter.run();
    }

    private void run() {
        CamelEvaluationCenterSwing gui = new CamelEvaluationCenterSwing();
        gui.constructApplicationFrame();
        TemplateTrigger trigger = gui.templateTrigger();
        TemplateTrigger sftpTemplateTrigger = gui.sftpTemplateTrigger();
        CamelContext context = createCamelContext();

        final ProducerTemplate template = context.createProducerTemplate();
        wireBasicTemplateTrigger(trigger, template);

        sftpTemplateTrigger.onTrigger(new UserCommand() {
            @Override
            public void given() {
                System.out.println("triggerd sftp upload");
                try{
                    template.sendBody("direct:sftpupload", sampleFileInResourceDirectory());
                }catch(CamelExecutionException ex){
                    System.out.println(ex);
                }
            }
        });

        addRoutesTo(context);
        new CamelContextIgnition(context, gui.startStop());
        gui.start();
    }

    private void wireBasicTemplateTrigger(TemplateTrigger trigger, final ProducerTemplate template) {
        trigger.onTrigger(new UserCommand() {
            @Override
            public void given() {
                template.sendBody("direct:trigger", "Banana Joe");
                template.sendBody("direct:trigger", sampleFileInResourceDirectory());
            }
        });
    }

    private File sampleFileInResourceDirectory() {
        return new File("camel/src/main/resources/sample.txt").getAbsoluteFile();
    }

    private void addRoutesTo(CamelContext context) {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").routeId("welcome wagon").to("stream:out");
                    from("direct:trigger").routeId("triggered welcome wagon").to("stream:out");
                    from("direct:sftpupload").routeId("upload to sftp server").to("sftp://localhost");
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CamelContext createCamelContext() {
        return new DefaultCamelContext();
    }
}
