package com.github.signed.sandboxes.spring.data.bg;

public final class JobBuilder {

    public static JobBuilder aJob() {
        return new JobBuilder();
    }

    public Long jobKey;
    private JobType type;
    private JobState state;
    private Long referenceKey;

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

    public Job build() {
        Job job = new Job();
        job.jobKey = jobKey;
        job.type = type;
        job.state = state;
        job.referenceKey = referenceKey;
        return job;
    }
}
