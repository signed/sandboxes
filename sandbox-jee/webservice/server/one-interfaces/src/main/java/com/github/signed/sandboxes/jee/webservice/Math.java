package com.github.signed.sandboxes.jee.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Math {
    @WebMethod
    public long sum(long a, long b);
}
