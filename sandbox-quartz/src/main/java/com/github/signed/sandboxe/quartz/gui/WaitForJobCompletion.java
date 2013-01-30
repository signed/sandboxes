package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;

class WaitForJobCompletion implements TriggerListener {
    private final Scheduler scheduler;
    private final TriggerKey triggerKey;
    private boolean stillRunning = true;
    private Integer lastCompletedExecution;

    public WaitForJobCompletion(Scheduler scheduler, TriggerKey triggerKey) {
        this.scheduler = scheduler;
        this.triggerKey = triggerKey;
    }

    @Override
    public String getName() {
        return "wait for job completion";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        //nothing to do
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        //nothing to do
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        stillRunning = false;
        lastCompletedExecution = context.getJobDetail().getJobDataMap().getInt("numberOfExecutions");
    }

    public Integer fetchResultFromJob() {
        new EnsureJobIsRunning(scheduler).ensureRunning(triggerKey);

        while (stillRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return -1;
            }
        }
        return lastCompletedExecution;
    }
}
