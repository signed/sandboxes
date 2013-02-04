package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

public class StopPeriodicalExecution implements Runnable {
    private final TriggerKey triggerKey;
    private final Scheduler scheduler;

    public StopPeriodicalExecution(TriggerKey triggerKey, Scheduler scheduler) {
        this.triggerKey = triggerKey;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {

        try {
            scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
