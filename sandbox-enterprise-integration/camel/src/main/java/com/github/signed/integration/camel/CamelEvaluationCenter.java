package com.github.signed.integration.camel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.SimpleRegistry;
import com.github.signed.integration.camel.gui.CamelContextIgnition;
import com.github.signed.integration.camel.gui.UserCommand;
import com.github.signed.integration.camel.gui.swing.CamelEvaluationCenterSwing;
import com.google.common.collect.Maps;

public class CamelEvaluationCenter {

    public static void main(String[] args) {
        CamelEvaluationCenter camelEvaluationCenter = new CamelEvaluationCenter();
        camelEvaluationCenter.run();
    }

    private void run() {
        CamelEvaluationCenterSwing gui = new CamelEvaluationCenterSwing();
        gui.constructApplicationFrame();

        CamelContext context = createCamelContext();

        final ProducerTemplate template = context.createProducerTemplate();
        gui.templateTrigger().onTrigger(basicTemplateCommand(template));
        gui.sftpTemplateTrigger().onTrigger(sftpUploadCommand(template));

        final ConsumerTemplate consumerTemplate = context.createConsumerTemplate();

        gui.sftpDownloadTrigger().onTrigger(new UserCommand() {
            @Override
            public void given() {
                System.out.println("starting sftp download");

                Parameters parameters = new Parameters();
                parameters.put("throwExceptionOnConnectFailed", "true");
                parameters.put("consumer.bridgeErrorHandler", "true");
                parameters.put("maximumReconnectAttempts", "0");
                parameters.put("username", "jenkins");
                parameters.put("password", "jenkins");
                parameters.put("knownHostsFile", "{{configuration.sftp.knownhosts.file}}");
                parameters.put("strictHostKeyChecking", "false");
                parameters.put("pollStrategy", "#consumer.poll.strategy.custom");
                parameters.put("sendEmptyMessageWhenIdle", "true");
                parameters.put("maxMessagesPerPoll", "1");
                parameters.put("move", ".done");
                parameters.put("moveFailed", ".error");

                Exchange exchange = consumerTemplate.receive("sftp://localhost/to_download?" + parameters.toArgumentString(), 3000);
                System.out.println("done polling");

                if (null != exchange.getException()) {
                    exchange.getException().printStackTrace();
                } else {
                    Message message = exchange.getIn();
                    if (null == message) {
                        System.out.println("no files found");
                    } else {
                        String content = message.getBody(String.class);
                        System.out.println(content);
                        DefaultMessage out = new DefaultMessage();
                        out.setFault(true);
                        exchange.setOut(out);
                    }
                }
                consumerTemplate.doneUoW(exchange);
            }
        });


        addRoutesTo(context);
        new CamelContextIgnition(context, gui.startStop());
        gui.start();
    }


    private UserCommand sftpUploadCommand(final ProducerTemplate template) {
        return new UserCommand() {
            @Override
            public void given() {
                System.out.println("triggerd sftp upload");
                try {
                    Map<String, Object> headers = Maps.newHashMap();
                    headers.put(Exchange.FILE_NAME, "the-file-to-upload.txt");
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("hello from the byte array input stream".getBytes());
                    template.sendBodyAndHeaders("direct:sftpupload", byteArrayInputStream, headers);
                } catch (CamelExecutionException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private UserCommand basicTemplateCommand(final ProducerTemplate template) {
        return new UserCommand() {
            @Override
            public void given() {
                template.sendBody("direct:trigger", sampleFileInResourceDirectory());
                template.sendBody("direct:trigger", "Banana Joe\n");
            }
        };
    }

    private File sampleFileInResourceDirectory() {
        return new File("camel/src/main/resources/sample.txt").getAbsoluteFile();
    }

    private void addRoutesTo(CamelContext context) {
        final Parameters options = new Parameters();
        options.put("knownHostsFile", "{{configuration.sftp.knownhosts.file}}");
        options.put("strictHostKeyChecking", "false");
        options.put("maximumReconnectAttempts", "0");
        options.put("username", "jenkins");
        options.put("password", "jenkins");
        options.put("disconnect", "true");

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:camel/src/main/resources/?noop=true&fileName=sample.txt").routeId("welcome wagon").to("stream:out");
                    from("direct:trigger").routeId("triggered welcome wagon").to("stream:out");
                    from("direct:sftpupload").routeId("upload to sftp server").to("sftp://localhost/upload?" + options.toArgumentString());
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
        registry.put("consumer.poll.strategy.custom", new CustomPollStrategy());

        PropertiesComponent propertiesComponent = new PropertiesComponent("ref:com.github.signed.configuration");
        context.addComponent("properties", propertiesComponent);
        return context;
    }
}
