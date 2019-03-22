package com.github.signed.sandboxe.quartz.domain;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;

class JobResult implements TriggerListener {
    private final CountDownLatch resultArrived = new CountDownLatch(1);
    private final String identifier;
    private Integer lastCompletedExecution = -1;

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
        try {
            lastCompletedExecution = context.getJobDetail().getJobDataMap().getInt("numberOfExecutions");
        } finally {
            resultArrived.countDown();
        }
    }

    public Integer waitFor() {
        try {
            resultArrived.await(15, SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return lastCompletedExecution;
    }
}