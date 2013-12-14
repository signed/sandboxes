package com.github.signed.integration.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.remote.FtpComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Camel {

    public static void main(String[] args) throws Exception {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        CamelContext context = new DefaultCamelContext();
        context.addComponent("ftp", new FtpComponent(context));
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("ftp://localhost:10021/boon?username=sally&password=secret&delay=2500").to("jms://filein");

                from("jms://filein").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Object camelFileName = exchange.getIn().getHeader("CamelFileName");
                        System.out.println("Straight from the queue: "+camelFileName);
                    }
                });
            }
        });


        context.start();
        Thread.sleep(100000);
        context.stop();
    }
}
