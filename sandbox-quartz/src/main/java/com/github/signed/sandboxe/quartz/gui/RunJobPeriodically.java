package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class RunJobPeriodically implements Runnable{
    private final Scheduler scheduler;
    private final JobFacts facts;

    public RunJobPeriodically(Scheduler scheduler, JobFacts facts) {
        this.scheduler = scheduler;
        this.facts = facts;
    }

    @Override
    public void run() {
        try {
            if(null == scheduler.getTrigger(facts.triggerKey)) {
                scheduler.scheduleJob(facts.triggerForPeriodicExecution(15 * 1000));
            }else{
                scheduler.rescheduleJob(facts.triggerKey, facts.triggerForPeriodicExecution(15 * 1000));
            }
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }
}