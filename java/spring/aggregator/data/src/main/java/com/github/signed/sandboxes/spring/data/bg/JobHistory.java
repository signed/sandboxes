package com.github.signed.sandboxes.spring.data.bg;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobHistory {

    public static JobHistory createFromUnsorted(List<Job> unsortedJobs) {
        List<Job> sortedEarlyFirst = unsortedJobs.stream().sorted(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return o1.created.compareTo(o2.created);
            }
        }).collect(Collectors.toList());
        return new JobHistory(sortedEarlyFirst);
    }

    private final List<Job> jobs;

    public JobHistory(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Job jobAt(int index) {
        return jobs.get(index);
    }

    public Optional<JobInContext> earliestPendingJob() {
        for (int i = 0; i < jobs.size(); ++i) {
            Job job = jobs.get(i);
            if (JobState.Pending.equals(job.state)) {
                return Optional.of(new JobInContext(i, this));
            }
        }
        return Optional.empty();
    }
}
