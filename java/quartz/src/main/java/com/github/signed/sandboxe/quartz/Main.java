package com.github.signed.sandboxe.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

    public static void main(String [] args) throws SchedulerException {

        new Main().run(new TestPlan());
    }


    private void run(Plan plan) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        plan.beforeSchedulerStarts(scheduler);
        scheduler.start();
        plan.afterSchedulerStarted(scheduler);
    }

}
