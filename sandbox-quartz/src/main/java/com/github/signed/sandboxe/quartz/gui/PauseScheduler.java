package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class PauseScheduler implements Runnable {
    private final Scheduler scheduler;

    public PauseScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
