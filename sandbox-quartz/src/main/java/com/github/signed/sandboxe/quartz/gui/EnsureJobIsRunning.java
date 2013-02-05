package com.github.signed.sandboxe.quartz.gui;

public class EnsureJobIsRunning {
    private final SchedulerFacade schedulerFacade;

    public EnsureJobIsRunning(SchedulerFacade schedulerFacade) {
        this.schedulerFacade = schedulerFacade;
    }

    public void ensureRunning(JobFacts facts) {
        if (schedulerFacade.isJobBeingExecuted(facts.triggerKey)) {
            System.out.println("job already running");
            return;
        }
        startJob(facts);
    }

    private void startJob(JobFacts facts) {
        System.out.println("starting job");
        schedulerFacade.rescheduleExistingOrFallback(facts.triggerKey, facts.triggerForOneImmediateExecution());
    }

}