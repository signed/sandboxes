package com.github.signed.sandboxes.jee.multipleears.one;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Logger;

@Singleton
@Startup
@LocalBean
public class OneService {

    @Inject
    public Logger logger;

    @PostConstruct
    public void myStart() {
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        logger.info("Start ....................");
        logger.info(getClass().getName() + ": " + new Date());
        logger.info("Finished ....................");
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        throw new RuntimeException("I should see this");
    }
}
