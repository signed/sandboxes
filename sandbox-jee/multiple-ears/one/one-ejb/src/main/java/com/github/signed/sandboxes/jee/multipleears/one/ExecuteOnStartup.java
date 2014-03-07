package com.github.signed.sandboxes.jee.multipleears.one;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class ExecuteOnStartup {

    @Inject
    public Logger logger;
    @EJB(lookup = "java:global/one-ear-1.0-SNAPSHOT/one-ejb-1.0-SNAPSHOT/OnlyOne")
    public One one;


    @PostConstruct
    public void myStart() {
        logger.info("about to call method on one");
        one.doStuff();
        logger.info("about to call method on one");
    }
}
