package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Trigger;

public class RunJobPeriodically implements Runnable {
    private final JobFacts facts;
    private final SchedulerFacade schedulerFacade;

    public RunJobPeriodically(JobFacts facts, SchedulerFacade schedulerFacade) {
        this.facts = facts;
        this.schedulerFacade = schedulerFacade;
    }

    @Override
    public void run() {
        Trigger trigger = facts.triggerForPeriodicExecution(15 * 1000);
        schedulerFacade.scheduleJobSmart(trigger);
    }
}