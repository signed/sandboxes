package com.github.signed.sandboxes.jee.webservice;

import javax.jws.WebService;

@WebService(targetNamespace = "http://example.org/wsdl")
public interface CalculatorWS {

    public int sum(int add1, int add2);

    public int multiply(int mul1, int mul2);
}