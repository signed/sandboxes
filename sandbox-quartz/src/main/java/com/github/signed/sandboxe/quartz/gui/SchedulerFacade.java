package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.List;

public class SchedulerFacade {

    private final Scheduler scheduler;

    public SchedulerFacade(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean isJobBeingExecuted(TriggerKey triggerKey) {
        try {
            List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext currentlyExecutingJob : currentlyExecutingJobs) {
                if (currentlyExecutingJob.getTrigger().getKey().equals(triggerKey)) {
                    return true;
                }
            }
            return false;
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean noTriggerIsRegistered(TriggerKey triggerKey) {
        try {
            return null == this.scheduler.getTrigger(triggerKey);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void scheduleJob(SimpleTrigger trigger) {
        try {
            this.scheduler.scheduleJob(trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void rescheduleJob(TriggerKey triggerKey, Trigger trigger) {
        try {
            this.scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void rescheduleJobWithKnownTrigger(TriggerKey triggerKey) {
        try {
            Trigger trigger = this.scheduler.getTrigger(triggerKey);
            rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }
}