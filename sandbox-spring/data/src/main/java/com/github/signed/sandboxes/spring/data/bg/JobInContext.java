package com.github.signed.sandboxes.spring.data.bg;

import java.util.Optional;

public class JobInContext {
    private final int index;
    private final JobHistory jobHistory;

    public JobInContext(int index, JobHistory jobHistory) {
        this.index = index;
        this.jobHistory = jobHistory;
    }

    public JobType type() {
        return job().type;
    }

    public Optional<JobInContext> precedingJob() {
        int beforeIndex = index - 1;
        return (beforeIndex < 0) ? Optional.empty() : Optional.of(new JobInContext(beforeIndex, jobHistory));
    }

    public boolean hasInProgressBefore(JobType jobType) {
        return false;
    }

    public boolean isInTerminalState() {
        return false;
    }

    private Job job() {
        return jobHistory.jobAt(index);
    }
}
