package com.github.signed.sandboxes.jee.webservice.client;

import com.github.signed.sandboxes.jee.webservice.HelloWorld;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class HelloWorldClient {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:9999/ws/hello?wsdl");
        QName qname = new QName("http://webservice.jee.sandboxes.signed.github.com/", "HelloWorldImplService");
        Service service = Service.create(url, qname);
        HelloWorld hello = service.getPort(HelloWorld.class);
        System.out.println(hello.getHelloWorldAsString("signed"));
    }

}