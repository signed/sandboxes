package com.github.signed.sandboxes.jee.multipleears.one;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OnlyOne implements One {

    @Inject
    public Logger logger;

    @EJB(lookup = "java:global/two-ear-1.0-SNAPSHOT/two-ejb-1.0-SNAPSHOT/OnlyTwo")
    public Two two;


    @Override
    public void doStuff(){
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        two.dumpToSystemOut();
        Argument argument = new Argument();
        argument.add("hallo");
        argument.add("Marcel");
        two.executeWith(argument);
        //two.access().invoke();
        logger.info("Start ....................");
        logger.info(getClass().getName() + ": " + new Date());
        logger.info("Finished ....................");
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

    }

}