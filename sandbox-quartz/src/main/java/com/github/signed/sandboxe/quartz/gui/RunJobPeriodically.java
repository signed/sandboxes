package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class RunJobPeriodically implements Runnable{

    private final int intervalInSeconds;

    private final Scheduler scheduler;
    private final JobFacts facts;

    public RunJobPeriodically(Scheduler scheduler, JobFacts facts) {
        this.scheduler = scheduler;
        this.facts = facts;
        intervalInSeconds = 15;
    }

    @Override
    public void run() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "periodically");
        SimpleScheduleBuilder secondSchedule = simpleSchedule().repeatForever().withIntervalInSeconds(intervalInSeconds).withMisfireHandlingInstructionNowWithRemainingCount();
        Trigger goodNightTrigger = TriggerBuilder.newTrigger().forJob(facts.jobKey).withIdentity(facts.triggerKey)
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();


        try {
            if(null == scheduler.getTrigger(facts.triggerKey)) {
                scheduler.scheduleJob(goodNightTrigger);
            }else{
                scheduler.rescheduleJob(facts.triggerKey, goodNightTrigger);
            }
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }
}
