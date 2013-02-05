package com.github.signed.sandboxe.quartz.gui;

import org.quartz.TriggerKey;

public class StopPeriodicalExecution implements Runnable {
    private final TriggerKey triggerKey;
    private final SchedulerFacade schedulerFacade;

    public StopPeriodicalExecution(TriggerKey triggerKey, SchedulerFacade schedulerFacade) {
        this.triggerKey = triggerKey;
        this.schedulerFacade = schedulerFacade;
    }

    @Override
    public void run() {
        schedulerFacade.unscheduleJob(this.triggerKey);
    }
}