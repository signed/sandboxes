package com.github.signed.sandboxe.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class SleepingJob implements Job {
    private String greeting = "silent";

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(greeting);
        for(int hoursSlept=0; hoursSlept<4; ++hoursSlept){
            try {
                Thread.sleep(1 * 1000l);
            } catch (InterruptedException e) {
                System.out.println("I was interrupted.");
            }

            System.out.print("snore ...");
        }
        System.out.println("Ring Ring");
        System.out.println("Morgen");
    }
}
