package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OneShot implements ActionListener {
    private final Scheduler scheduler;
    private final JobKey jobKey;

    public OneShot(Scheduler scheduler, JobKey jobKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            doStuff();
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void doStuff() throws SchedulerException {
        List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
        if (triggersOfJob.isEmpty()) {
            return;
        }

        Trigger trigger = triggersOfJob.get(0);
        List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext currentlyExecutingJob : currentlyExecutingJobs) {

        }
    }
}
