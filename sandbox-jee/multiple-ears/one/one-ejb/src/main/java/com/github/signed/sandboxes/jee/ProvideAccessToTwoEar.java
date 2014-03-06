package com.github.signed.sandboxes.jee;

import javax.enterprise.inject.Produces;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.github.signed.sandboxes.jee.multipleears.one.Two;

public class ProvideAccessToTwoEar {

    @Produces
    public Two produceService() throws NamingException {
        Context ctx = new InitialContext();
        return (Two) ctx.lookup("java:global/two-ear-1.0-SNAPSHOT/two-ejb-1.0-SNAPSHOT/OnlyTwo");
    }
}
