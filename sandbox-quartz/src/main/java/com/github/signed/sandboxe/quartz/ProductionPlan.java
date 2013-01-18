package com.github.signed.sandboxe.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class ProductionPlan implements Main.Plan {

    @Override
    public void beforeSchedulerStarts(Scheduler scheduler) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SleepingJob.class)
                .withIdentity("sleep-job")
                .usingJobData(jobData())
                .build();
        SimpleScheduleBuilder schedule = simpleSchedule()
                .withIntervalInMilliseconds(5 * 1000l)
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("sleep-trigger")
                .startNow()
                .withSchedule(schedule)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    private JobDataMap jobData() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "production");
        return jobDataMap;
    }

    @Override
    public void afterSchedulerStarted(Scheduler scheduler) {
        //nothing to do
    }
}
