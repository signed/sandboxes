package com.github.signed.sandboxe.quartz;

import com.github.signed.sandboxe.quartz.domain.SleepingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class TestPlan implements Plan {
    @Override
    public void beforeSchedulerStarts(Scheduler scheduler) {
        //noting to do

    }

    @Override
    public void afterSchedulerStarted(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SleepingJob.class).withIdentity("greeting job", "polite").storeDurably().build();
        scheduler.addJob(jobDetail, false);
        JobKey jobKey = jobDetail.getKey();

        SimpleScheduleBuilder secondSchedule = simpleSchedule().withRepeatCount(3).withIntervalInSeconds(7);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "Gute Nacht");
        Trigger goodNightTrigger = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity("good night trigger", "polite")
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();

        scheduler.getListenerManager().addTriggerListener(new SystemOutInteractionLogger(), KeyMatcher.keyEquals(goodNightTrigger.getKey()));
        scheduler.scheduleJob(goodNightTrigger);
    }
}