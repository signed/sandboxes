package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.util.Properties;

public class JobScheduler {
    public Scheduler createCustomizedScheduler() throws IOException, SchedulerException {
        Properties schedulerProperties = new Properties();
        schedulerProperties.load(Scheduler.class.getResourceAsStream("/org/quartz/quartz.properties"));
        schedulerProperties.put("org.quartz.jobStore.misfireThreshold", "1000");

        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(schedulerProperties);
        return factory.getScheduler();
    }
}
