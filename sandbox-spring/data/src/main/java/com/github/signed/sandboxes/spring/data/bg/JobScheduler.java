package com.github.signed.sandboxes.spring.data.bg;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.signed.sandboxes.spring.data.bg.JobState.Cancelled;
import static com.github.signed.sandboxes.spring.data.bg.JobState.Done;
import static com.github.signed.sandboxes.spring.data.bg.JobState.InProgress;
import static com.github.signed.sandboxes.spring.data.bg.JobType.CancelAction;
import static com.github.signed.sandboxes.spring.data.bg.JobType.ExecuteAction;
import static java.util.stream.Collectors.groupingBy;

public class JobScheduler {
    private final JobsRepository jobsRepository;

    public JobScheduler(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    public void schedule() {
        List<Job> jobs = jobsRepository.letsSeeAllJobsForAReferenceKeyWhereAtLeasOneIs(JobState.Pending);
        List<JobHistory> jobHistories = jobs.stream().collect(groupingBy(job -> job.referenceKey))
                .values().stream().map(JobHistory::createFromUnsorted).collect(Collectors.toList());
        jobHistories.parallelStream().forEach(this::update);
    }

    private void update(JobHistory jobHistory) {
        JobInContext pendingJob = jobHistory.earliestPendingJob().get();
        Optional<JobInContext> maybePrecedingJob = pendingJob.precedingJob();
        switch (pendingJob.type()) {
            case ExecuteAction:
                if (!maybePrecedingJob.isPresent()) {
                    set(pendingJob, InProgress);
                    break;
                }
                maybePrecedingJob.ifPresent(precedingJob -> {
                    if (precedingJob.isOfType(ExecuteAction)) {
                        set(pendingJob, Done);
                        return;
                    }
                    if (precedingJob.isOfType(CancelAction)) {
                        if (precedingJob.isInTerminalState()) {
                            set(pendingJob, InProgress);
                            return;
                        }
                    }
                });
                break;
            case CancelAction:
                if (!maybePrecedingJob.isPresent()) {
                    set(pendingJob, Done);
                    break;
                }
                maybePrecedingJob.ifPresent(precedingJob -> {
                    if (precedingJob.isOfType(ExecuteAction)) {
                        set(precedingJob, Cancelled);
                        set(pendingJob, InProgress);
                        return;
                    }
                    if(precedingJob.isOfType(CancelAction)){
                        set(pendingJob, Done);
                        return;
                    }
                });
                break;
            default:
                throw new IllegalStateException("Should not happen");
        }
    }

    private void set(JobInContext pendingJob, JobState newState) {

    }
}
