package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.KeyMatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OneShot implements ActionListener {
    private final Scheduler scheduler;
    private final JobKey jobKey;
    private final TriggerKey triggerKey;

    public OneShot(Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
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
        WaitForJobCompletion waitForJobCompletion = new WaitForJobCompletion(scheduler, triggerKey);
        try {
            scheduler.getListenerManager().addTriggerListener(waitForJobCompletion, KeyMatcher.keyEquals(triggerKey));
            Integer lastExecution = waitForJobCompletion.fetchResultFromJob();
            System.out.println("last execution was " + lastExecution);
        } finally {
            scheduler.getListenerManager().removeTriggerListener(waitForJobCompletion.getName());
        }
    }

    private void triggerJobWithNewData() throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("numberOfExecutions", 42);
        scheduler.triggerJob(jobKey, dataMap);
    }


}
