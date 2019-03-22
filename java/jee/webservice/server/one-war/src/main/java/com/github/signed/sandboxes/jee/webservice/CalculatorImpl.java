package com.github.signed.sandboxes.jee.webservice;

import com.github.signed.sandboxes.jee.async.AsyncBean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

@Stateless
@WebService(
        portName = "CalculatorPort",
        serviceName = "CalculatorService",
        targetNamespace = "http://example.org/wsdl",
        endpointInterface = "com.github.signed.sandboxes.jee.webservice.CalculatorWS")
public class CalculatorImpl implements CalculatorWS {

    @Inject
    AsyncBean asyncBean;

    @Override
    public int sum(int add1, int add2) {
        return add1 + add2;
    }

    @Override
    public int multiply(int mul1, int mul2) {
        return mul1 * mul2;
    }
}
