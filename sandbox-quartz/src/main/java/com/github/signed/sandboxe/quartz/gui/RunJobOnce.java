package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.KeyMatcher;

class RunJobOnce implements Runnable {
    private final Scheduler scheduler;
    private final JobKey jobKey;
    private final TriggerKey triggerKey;

    public RunJobOnce(Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
    }

    @Override
    public void run() {
        try {
            doStuff();
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void doStuff() throws SchedulerException {
        String threadId = Long.toString(Thread.currentThread().getId());
        WaitForJobCompletion waitForJobCompletion = new WaitForJobCompletion(scheduler, triggerKey, jobKey, threadId);
        try {
            scheduler.getListenerManager().addTriggerListener(waitForJobCompletion, KeyMatcher.keyEquals(triggerKey));
            Integer lastExecution = waitForJobCompletion.fetchResultFromJob();
            System.out.println("last execution was " + lastExecution);
        } finally {
            scheduler.getListenerManager().removeTriggerListener(waitForJobCompletion.getName());
        }
    }
}
