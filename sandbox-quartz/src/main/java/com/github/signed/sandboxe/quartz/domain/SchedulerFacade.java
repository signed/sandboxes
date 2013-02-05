package com.github.signed.sandboxe.quartz.domain;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.KeyMatcher;

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

    public void scheduleJob(Trigger trigger) {
        try {
            this.scheduler.scheduleJob(trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void rescheduleJob(Trigger trigger) {
        this.rescheduleJob(trigger.getKey(), trigger);
    }

    public void rescheduleJob(TriggerKey triggerKey, Trigger trigger) {
        try {
            this.scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addTriggerListener(TriggerListener jobResult, TriggerKey triggerKey) {
        try {
            this.scheduler.getListenerManager().addTriggerListener(jobResult, KeyMatcher.keyEquals(triggerKey));
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeTriggerListener(TriggerListener triggerListener) {
        try {
            this.scheduler.getListenerManager().removeTriggerListener(triggerListener.getName());
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unscheduleJob(TriggerKey triggerKey) {
        try {
            this.scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void scheduleJobSmart(Trigger trigger) {
        if (noTriggerIsRegistered(trigger.getKey())) {
            scheduleJob(trigger);
        } else {
            rescheduleJob(trigger);
        }
    }

    public void rescheduleExistingOrFallback(TriggerKey triggerKey, Trigger fallback) {
        if (noTriggerIsRegistered(triggerKey)) {
            scheduleJob(fallback);
        } else {
            rescheduleJobWithKnownTrigger(triggerKey);
        }
    }

    private void rescheduleJobWithKnownTrigger(TriggerKey triggerKey) {
        try {
            Trigger trigger = this.scheduler.getTrigger(triggerKey);
            rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isRunningPeriodically(TriggerKey triggerKey) {
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            return null != trigger && null == trigger.getFinalFireTime();
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }
}