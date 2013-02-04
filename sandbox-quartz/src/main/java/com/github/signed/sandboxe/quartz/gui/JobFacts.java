package com.github.signed.sandboxe.quartz.gui;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

public class JobFacts {
    public final JobKey jobKey;
    public final TriggerKey triggerKey;

    public JobFacts(JobKey jobKey, TriggerKey triggerKey) {
        this.jobKey = jobKey;
        this.triggerKey = triggerKey;
    }
}
