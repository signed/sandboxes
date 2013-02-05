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

    public void ensureRunning(JobFacts facts) {
        try {
            ensureRunningWithException(facts);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureRunningWithException(JobFacts facts) throws SchedulerException {
        if (isJobRunning(facts.triggerKey)) {
            System.out.println("job already running");
        } else {
            System.out.println("starting job");

            if (null == scheduler.getTrigger(facts.triggerKey)) {
                System.out.print("with one shoot trigger");
                scheduler.scheduleJob(facts.triggerForOneImmediateExecution());
            } else {
                System.out.println("reschedule");
                scheduler.rescheduleJob(facts.triggerKey, scheduler.getTrigger(facts.triggerKey));
            }
        }
    }

    private boolean isJobRunning(TriggerKey triggerKey) throws SchedulerException {
        List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext currentlyExecutingJob : currentlyExecutingJobs) {
            if (currentlyExecutingJob.getTrigger().getKey().equals(triggerKey)) {
                return true;
            }
        }
        return false;
    }
}
