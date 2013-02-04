package com.github.signed.sandboxe.quartz.gui;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.KeyMatcher;

class RunJobOnce implements Runnable {
    private final Scheduler scheduler;
    private final JobFacts facts;

    public RunJobOnce(Scheduler scheduler, JobFacts facts) {
        this.scheduler = scheduler;
        this.facts = facts;
    }

    @Override
    public void run() {
        try {
            doStuff();
        } catch (SchedulerException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void doStuff() throws SchedulerException {
        String threadId = Long.toString(Thread.currentThread().getId());
        JobResult jobResult = new JobResult(threadId);
        try {
            scheduler.getListenerManager().addTriggerListener(jobResult, KeyMatcher.keyEquals(facts.triggerKey));
            new EnsureJobIsRunning(scheduler).ensureRunning(facts);
            Integer lastExecution = jobResult.waitFor();
            System.out.println("last execution was " + lastExecution);
        } finally {
            scheduler.getListenerManager().removeTriggerListener(jobResult.getName());
        }
    }

}
