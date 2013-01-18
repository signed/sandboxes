package com.github.signed.sandboxe.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SystemOutInteractionLogger implements TriggerListener {

    @Override
    public String getName() {
        return "this is my listener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        printTrigger(trigger, "fired/started");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        printTrigger(trigger, "veto");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        printTrigger(trigger, "misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        printTrigger(trigger, "completed");
    }

    private void printTrigger(Trigger trigger, String message) {
        System.out.println(" [trigger.getKey()] " + message);
    }
}
