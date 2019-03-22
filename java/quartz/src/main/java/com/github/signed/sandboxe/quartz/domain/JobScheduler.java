package com.github.signed.sandboxe.quartz.domain;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class JobScheduler {
    private final Properties schedulerProperties = new Properties();

    public void loadDefaultPropertiesFromQuartz() throws IOException {
        schedulerProperties.load(Scheduler.class.getResourceAsStream("/org/quartz/quartz.properties"));
    }

    public void misfireThreshold(long amount, TimeUnit unit) {
        schedulerProperties.put("org.quartz.jobStore.misfireThreshold", Long.toString(unit.toMillis(amount)));
    }

    public Scheduler createCustomizedScheduler() throws IOException, SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(schedulerProperties);
        return factory.getScheduler();
    }
}
