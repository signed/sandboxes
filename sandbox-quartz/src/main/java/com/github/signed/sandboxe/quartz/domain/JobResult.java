package com.github.signed.sandboxe.quartz.domain;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class JobResult implements TriggerListener {
    private final String identifier;
    private boolean stillRunning = true;
    private Integer lastCompletedExecution;

    public JobResult(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return "wait for result " + identifier;
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

    public Integer getResult() {
        return lastCompletedExecution;
    }

    public boolean stillRunning() {
        return stillRunning;
    }

    public Integer waitFor() {
        while (stillRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return -1;
            }
        }
        return getResult();
    }
}