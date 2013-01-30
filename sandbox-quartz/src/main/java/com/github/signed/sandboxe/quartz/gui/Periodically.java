package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class Periodically implements ActionListener {
    private final Scheduler scheduler;
    private final JobKey jobKey;
    private final TriggerKey triggerKey;

    public Periodically(Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("greeting", "Periodically");
        SimpleScheduleBuilder secondSchedule = simpleSchedule().withRepeatCount(3).withIntervalInSeconds(15);
        Trigger goodNightTrigger = TriggerBuilder.newTrigger().forJob(jobKey).withIdentity(triggerKey)
                .usingJobData(jobDataMap).withSchedule(secondSchedule).build();


        try {
            //scheduler.getListenerManager().addTriggerListener(new SystemOutInteractionLogger(), KeyMatcher.keyEquals(goodNightTrigger.getKey()));
            scheduler.scheduleJob(goodNightTrigger);
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }
}
