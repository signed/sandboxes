package com.github.signed.sandboxe.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface Plan {
    void beforeSchedulerStarts(Scheduler scheduler) throws SchedulerException;

    void afterSchedulerStarted(Scheduler scheduler) throws SchedulerException;
}
