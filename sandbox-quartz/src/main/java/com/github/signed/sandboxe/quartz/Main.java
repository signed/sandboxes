package com.github.signed.sandboxe.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

    public static void main(String [] args) throws SchedulerException {
        new Main().run();
    }

    private void run() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail jobDetail = JobBuilder.newJob(TheJob.class).withIdentity("greeting job", "polite").storeDurably().build();
        scheduler.addJob(jobDetail, false);

        Trigger goodMorningTrigger = createTrigger(jobDetail, "good morning trigger", "Guten Morgen", 1);
        Trigger goodNightTrigger = createTrigger(jobDetail, "good night trigger", "Gute Nacht", 5);

        scheduler.scheduleJob(goodMorningTrigger);
        scheduler.scheduleJob(goodNightTrigger);

    }

    private Trigger createTrigger(JobDetail jobDetail, String name, String greeting, int seconds) {
        SimpleScheduleBuilder secondSchedule = SimpleScheduleBuilder.repeatSecondlyForever(seconds);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", greeting);
        return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(name, "polite").usingJobData(jobDataMap).startNow().withSchedule(secondSchedule).build();
    }
}
