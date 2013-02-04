package com.github.signed.sandboxe.quartz.gui;

import com.github.signed.sandboxe.quartz.SystemOutInteractionLogger;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class RunJobPeriodically implements Runnable{

    private final int intervalInSeconds;

    private final Scheduler scheduler;
    private final JobKey jobKey;
    private final TriggerKey triggerKey;

    public RunJobPeriodically(Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
        intervalInSeconds = 15;
    }

    @Override
    public void run() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "ExecuteRunnableOnAction");
        SimpleScheduleBuilder secondSchedule = simpleSchedule().repeatForever().withIntervalInSeconds(intervalInSeconds).withMisfireHandlingInstructionNowWithRemainingCount();
        Trigger goodNightTrigger = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey)
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();


        try {
            scheduler.getListenerManager().addTriggerListener(new SystemOutInteractionLogger(), KeyMatcher.keyEquals(goodNightTrigger.getKey()));
            scheduler.scheduleJob(goodNightTrigger);
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }
}
