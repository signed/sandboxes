package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

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
        jobDataMap.put("greeting", "periodically");
        SimpleScheduleBuilder secondSchedule = simpleSchedule().repeatForever().withIntervalInSeconds(intervalInSeconds).withMisfireHandlingInstructionNowWithRemainingCount();
        Trigger goodNightTrigger = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey)
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();


        try {
            if(null == scheduler.getTrigger(triggerKey)) {
                scheduler.scheduleJob(goodNightTrigger);
            }else{
                scheduler.rescheduleJob(triggerKey, goodNightTrigger);
            }
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }
}
