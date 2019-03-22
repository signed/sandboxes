package com.github.signed.sandboxe.quartz.domain;

class EnsureJobIsRunning {
    private final SchedulerFacade schedulerFacade;

    public EnsureJobIsRunning(SchedulerFacade schedulerFacade) {
        this.schedulerFacade = schedulerFacade;
    }

    public void ensureRunning(JobFacts facts) {
        if (schedulerFacade.isJobBeingExecuted(facts.triggerKey)) {
            System.out.print("-job already running-");
            return;
        }
        startJob(facts);
    }

    private void startJob(JobFacts facts) {
        System.out.print("-starting job-");
        schedulerFacade.rescheduleExistingOrFallback(facts.triggerKey, facts.triggerForOneImmediateExecution());
    }
}