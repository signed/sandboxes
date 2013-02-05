package com.github.signed.sandboxe.quartz.gui;

public class EnsureJobIsRunning {
    private final SchedulerFacade schedulerFacade;

    public EnsureJobIsRunning(SchedulerFacade schedulerFacade) {
        this.schedulerFacade = schedulerFacade;
    }

    public void ensureRunning(JobFacts facts) {
        if (schedulerFacade.isJobBeingExecuted(facts.triggerKey)) {
            System.out.println("job already running");
        } else {
            System.out.println("starting job");
            if (schedulerFacade.noTriggerIsRegistered(facts.triggerKey)) {
                System.out.print("with one shoot trigger");
                schedulerFacade.scheduleJob(facts.triggerForOneImmediateExecution());
            } else {
                System.out.println("reschedule");
                schedulerFacade.rescheduleJobWithKnownTrigger(facts.triggerKey);
            }
        }
    }
}