package com.github.signed.sandboxe.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

    public static void main(String [] args) throws SchedulerException {
        new Main().run();
    }

    private void run() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

    }
}
