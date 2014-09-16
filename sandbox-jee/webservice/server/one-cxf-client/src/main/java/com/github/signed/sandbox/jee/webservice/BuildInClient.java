package com.github.signed.sandbox.jee.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.joda.time.LocalDate;

import com.github.signed.sandboxes.jee.data.in.ImportWebservice;
import com.github.signed.sandboxes.jee.webservice.CalculatorWS;

public class BuildInClient {

    public static void main(String [] args) throws MalformedURLException {

//        CalculatorWS calculator = new BuildInClient().calculator();
//        System.out.println(calculator.sum(4, 6));
//        System.out.println(calculator.multiply(3, 4));

        ImportWebservice importWebservice = new BuildInClient().dataImport();
        importWebservice.importDataForDay(new LocalDate().toDate(), "the best data ever");
        importWebservice.importDataForDay(new LocalDate().plusDays(2).toDate(), "the best data ever");


    }

    private ImportWebservice dataImport() throws MalformedURLException {
        Service calculatorService = Service.create(
                new URL("http://localhost:8080/the-war/ImportService/ImportWebserviceDefault?wsdl"),
                new QName("http://example.org/wsdl", "ImportService"));
        return calculatorService.getPort(ImportWebservice.class);
    }

    private CalculatorWS calculator() throws MalformedURLException {
        Service calculatorService = Service.create(
                new URL("http://localhost:8080/the-war/CalculatorService/CalculatorImpl?wsdl"),
                new QName("http://example.org/wsdl", "CalculatorService"));
        return calculatorService.getPort(CalculatorWS.class);

    }
}
