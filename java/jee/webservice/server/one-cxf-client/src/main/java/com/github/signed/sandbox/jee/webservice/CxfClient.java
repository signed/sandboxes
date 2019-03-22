package com.github.signed.sandbox.jee.webservice;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.github.signed.sandboxes.jee.webservice.CalculatorWS;

public class CxfClient {

    public static void main(String args[]) throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(CalculatorWS.class);
        factory.setAddress("http://localhost:8080/the-war/CalculatorService/CalculatorImpl");
        CalculatorWS client = (CalculatorWS) factory.create();

        System.out.println("Server said: " + client.sum(3,4));
        System.out.println("Server said: " + client.multiply(3,4));

    }
}
