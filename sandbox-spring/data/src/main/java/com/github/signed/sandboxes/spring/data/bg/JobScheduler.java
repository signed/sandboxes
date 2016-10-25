package com.github.signed.sandboxes.spring.data.bg;

import java.util.List;
import java.util.stream.Collectors;

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

    private void update(JobHistory jobHistory){

    }
}
