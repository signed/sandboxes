package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;

class WaitForJobCompletion implements TriggerListener {
    private final Scheduler scheduler;
    private final TriggerKey triggerKey;
    private final JobKey jobKey;
    private final String identifier;
    private boolean stillRunning = true;
    private Integer lastCompletedExecution;

    public WaitForJobCompletion(Scheduler scheduler, TriggerKey triggerKey, JobKey jobKey, String identifier) {
        this.scheduler = scheduler;
        this.triggerKey = triggerKey;
        this.jobKey = jobKey;
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return "wait for job completion "+identifier;
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
        logIfTriggerStillInScheduler();
    }

    private void logIfTriggerStillInScheduler() {
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            System.out.println("trigger with key " + triggerKey + " is "+ trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Integer fetchResultFromJob() {
        new EnsureJobIsRunning(scheduler).ensureRunning(triggerKey, jobKey);

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
