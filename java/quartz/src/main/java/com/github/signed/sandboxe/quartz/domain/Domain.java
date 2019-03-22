package com.github.signed.sandboxe.quartz.domain;

import org.quartz.Trigger;

public class Domain {

    private final JobFacts facts;
    private final SchedulerFacade schedulerFacade;

    public Domain(JobFacts facts, SchedulerFacade schedulerFacade) {
        this.facts = facts;
        this.schedulerFacade = schedulerFacade;
    }

    public Integer getResultOfCurrentExecution() {
        String threadId = Long.toString(Thread.currentThread().getId());
        JobResult jobResult = new JobResult(threadId);
        try {
            schedulerFacade.addTriggerListener(jobResult, facts.triggerKey);
            new EnsureJobIsRunning(schedulerFacade).ensureRunning(facts);
            return jobResult.waitFor();
        } finally {
            schedulerFacade.removeTriggerListener(jobResult);
        }
    }

    public void stopPeriodicExecution() {
        schedulerFacade.unscheduleJob(this.facts.triggerKey);
    }

    public boolean isRunningPeriodically(){
        return schedulerFacade.isRunningPeriodically(this.facts.triggerKey);
    }

    public void runPeriodically() {
        Trigger trigger = facts.triggerForPeriodicExecution(15 * 1000);
        schedulerFacade.scheduleJobSmart(trigger);
    }
}
