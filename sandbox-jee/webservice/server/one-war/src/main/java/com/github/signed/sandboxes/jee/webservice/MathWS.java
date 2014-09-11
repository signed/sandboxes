package com.github.signed.sandboxes.jee.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.signed.sandboxes.jee.webservice.Math", serviceName = "MathWS")
public class MathWS implements Math {

    @Override
    public long sum(long a, long b) {
        System.out.println("Summing " + a + " + " + b);
        return a + b;
    }
}
