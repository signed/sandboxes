package com.github.signed.integration.camel;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import com.github.signed.integration.camel.gui.CamelContextIgnition;
import com.github.signed.integration.camel.gui.UserCommand;
import com.github.signed.integration.camel.gui.swing.CamelEvaluationCenterSwing;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

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
                try {
                    Map<String, Object> headers = Maps.newHashMap();
                    headers.put(Exchange.FILE_NAME, "the-file.zip");
                    template.sendBodyAndHeaders("direct:sftpupload", sampleFileInResourceDirectory(), headers);
                } catch (CamelExecutionException ex) {
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
        Map<String, String> options = Maps.newHashMap();
        options.put("knownHostsFile", "{{configuration.sftp.knownhosts.file}}");
        options.put("maximumReconnectAttempts", "0");
        options.put("strictHostKeyChecking", "yes");
        options.put("username", "jenkins");
        options.put("password", "jenkins");
        options.put("disconnect", "true");

        final String arguments = FluentIterable.from(options.entrySet()).transform(new Function<Map.Entry<String, String>, String>() {
            @Override
            public String apply(Map.Entry<String, String> input) {
                return input.getKey() + "=" + input.getValue();
            }
        }).join(Joiner.on("&"));

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").routeId("welcome wagon").to("stream:out");
                    from("direct:trigger").routeId("triggered welcome wagon").to("stream:out");
                    from("direct:sftpupload").routeId("upload to sftp server").to("sftp://localhost/upload?" + arguments);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CamelContext createCamelContext() {
        SimpleRegistry registry = new SimpleRegistry();
        DefaultCamelContext context = new DefaultCamelContext(registry);
        Properties theProperties = new Properties();
        theProperties.put("configuration.sftp.knownhosts.file", "/tmp/camel/known_hosts");
        registry.put("com.github.signed.configuration", theProperties);
        PropertiesComponent propertiesComponent = new PropertiesComponent("ref:com.github.signed.configuration");
        context.addComponent("properties", propertiesComponent);
        return context;
    }
}
