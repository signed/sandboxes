package com.github.signed.sandboxe.quartz.gui.actions;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

public class ListKnownTriggers implements Runnable {
    private final Scheduler scheduler;
    private final JobKey jobKey;

    public ListKnownTriggers(Scheduler scheduler, JobKey jobKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
    }

    @Override
    public void run() {
        try {
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
            System.out.println("known triggers: ");
            if(triggersOfJob.isEmpty()){
                System.out.println("none");
            }
            for (Trigger trigger : triggersOfJob) {
                System.out.println(trigger);
            }
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }
    }
}
