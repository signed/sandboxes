package com.github.signed.sandbox.jee.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.github.signed.sandboxes.jee.webservice.CalculatorWS;

public class BuildInClient {

    public static void main(String [] args) throws MalformedURLException {

        Service calculatorService = Service.create(
                new URL("http://localhost:8080/the-war/CalculatorService/CalculatorImpl?wsdl"),
                new QName("http://example.org/wsdl", "CalculatorService"));

        CalculatorWS calculator = calculatorService.getPort(CalculatorWS.class);
        System.out.println(calculator.sum(4, 6));
        System.out.println(calculator.multiply(3, 4));
    }
}
