package com.github.signed.sandboxes.spring.data.bg;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public final class JobBuilder {

    public static JobBuilder aJob() {
        return new JobBuilder();
    }

    private Long jobKey;
    private JobType type;
    private JobState state;
    private Long referenceKey;
    private LocalDateTime created;

    private JobBuilder() {
    }

    public JobBuilder withJobKey(Long jobKey) {
        this.jobKey = jobKey;
        return this;
    }

    public JobBuilder withType(JobType jobType) {
        this.type = jobType;
        return this;
    }

    public JobBuilder withState(JobState state) {
        this.state = state;
        return this;
    }

    public JobBuilder withReferenceKey(Long referenceKey) {
        this.referenceKey = referenceKey;
        return this;
    }

    public JobBuilder created(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public Job build() {
        Job job = new Job();
        job.jobKey = jobKey;
        job.type = type;
        job.state = state;
        job.referenceKey = referenceKey;
        job.created = Date.from(Optional.ofNullable(created).orElse(LocalDateTime.now()).toInstant(ZoneOffset.UTC));
        return job;
    }
}
