package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

import java.util.List;

public class EnsureJobIsRunning {
    private final Scheduler scheduler;

    public EnsureJobIsRunning(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void ensureRunning(TriggerKey triggerKey){
        try {
            save(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(TriggerKey triggerKey) throws SchedulerException {
        if (isJobRunning(triggerKey)) {
            System.out.println("job already running");
        } else {
            System.out.println("starting job");
            scheduler.rescheduleJob(triggerKey, scheduler.getTrigger(triggerKey));
        }
    }

    private boolean isJobRunning(TriggerKey triggerKey) throws SchedulerException {
        List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext currentlyExecutingJob : currentlyExecutingJobs) {
            if(currentlyExecutingJob.getTrigger().getKey().equals(triggerKey)){
                return true;
            }
        }
        return false;
    }
}
