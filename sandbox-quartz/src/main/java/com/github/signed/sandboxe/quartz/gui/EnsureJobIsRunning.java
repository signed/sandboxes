package com.github.signed.sandboxe.quartz.gui;

import org.quartz.SimpleTrigger;

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

        SimpleTrigger oneImmediateExecution = facts.triggerForOneImmediateExecution();

        if (schedulerFacade.noTriggerIsRegistered(facts.triggerKey)) {
            System.out.print("with one shoot trigger");
            schedulerFacade.scheduleJob(oneImmediateExecution);
        } else {
            System.out.println("reschedule");
            schedulerFacade.rescheduleJobWithKnownTrigger(facts.triggerKey);
        }
    }
}