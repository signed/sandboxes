package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import java.util.List;

public class EnsureJobIsRunning {
    private final Scheduler scheduler;

    public EnsureJobIsRunning(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void ensureRunning(TriggerKey triggerKey, JobKey jobKey) {
        try {
            ensureRunningWithException(triggerKey, jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureRunningWithException(TriggerKey triggerKey, JobKey jobKey) throws SchedulerException {
        if (isJobRunning(triggerKey)) {
            System.out.println("job already running");
        } else {
            System.out.println("starting job");

            if (null == scheduler.getTrigger(triggerKey)) {
                System.out.print("with one shoot trigger");
                SimpleScheduleBuilder once = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0);
                TriggerBuilder<SimpleTrigger> triggerTriggerBuilder = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey).withSchedule(once).startNow();
                scheduler.scheduleJob(triggerTriggerBuilder.build());
            } else {
                System.out.println("reschedule");
                scheduler.rescheduleJob(triggerKey, scheduler.getTrigger(triggerKey));
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
