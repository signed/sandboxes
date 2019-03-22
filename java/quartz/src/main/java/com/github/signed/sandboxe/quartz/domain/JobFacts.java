package com.github.signed.sandboxe.quartz.domain;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class JobFacts {
    public final JobKey jobKey;
    public final TriggerKey triggerKey;

    public JobFacts(JobKey jobKey, TriggerKey triggerKey) {
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
    }

    public SimpleTrigger triggerForOneImmediateExecution() {
        SimpleScheduleBuilder once = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0);
        TriggerBuilder<SimpleTrigger> triggerTriggerBuilder = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey).withSchedule(once).startNow();
        return triggerTriggerBuilder.build();
    }

    public Trigger triggerForPeriodicExecution(int intervalInMillis) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "periodically");
        SimpleScheduleBuilder secondSchedule = simpleSchedule().repeatForever().withIntervalInMilliseconds(intervalInMillis).withMisfireHandlingInstructionNowWithRemainingCount();
        return TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey)
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();
    }
}
