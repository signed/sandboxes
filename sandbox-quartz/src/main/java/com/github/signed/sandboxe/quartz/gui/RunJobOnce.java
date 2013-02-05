package com.github.signed.sandboxe.quartz.gui;

class RunJobOnce implements Runnable {
    private final JobFacts facts;
    private SchedulerFacade schedulerFacade;

    public RunJobOnce(JobFacts facts, SchedulerFacade schedulerFacade) {
        this.facts = facts;
        this.schedulerFacade = schedulerFacade;
    }

    @Override
    public void run() {
        String threadId = Long.toString(Thread.currentThread().getId());
        JobResult jobResult = new JobResult(threadId);
        try {
            schedulerFacade.addTriggerListener(jobResult, facts.triggerKey);
            new EnsureJobIsRunning(schedulerFacade).ensureRunning(facts);
            Integer lastExecution = jobResult.waitFor();
            System.out.println("last execution was " + lastExecution);
        } finally {
            schedulerFacade.removeTriggerListener(jobResult);
        }
    }

}