package com.github.signed.sandboxe.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TheJob implements Job {
    private String greeting = "silent";

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(greeting);
        if("Gute Nacht".equals(greeting)){
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                System.out.println("I was interrupted.");
            }
            System.out.println("Ring Ring");
        }
    }
}
