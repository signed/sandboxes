package com.github.signed.sandboxe.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SleepingJob implements Job {
    public static final int HoursToSleep = 4;
    private String greeting = "silent";
    private Integer numberOfExecutionsUntilNow;

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void setNumberOfExecutions(Integer numberOfExecutionsUntilNow){
        this.numberOfExecutionsUntilNow = numberOfExecutionsUntilNow;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        int currentExecution = numberOfExecutionsUntilNow + 1;
        System.out.println("this is execution #" + currentExecution);
        System.out.println(greeting);
        for(int hoursSlept=0; hoursSlept< HoursToSleep; ++hoursSlept){
            try {
                Thread.sleep(1 * 1000l);
            } catch (InterruptedException e) {
                System.out.println("I was interrupted.");
            }

            System.out.print("snore ...");
        }
        System.out.println("Ring Ring");
        System.out.println("Morgen\n");
        context.getJobDetail().getJobDataMap().put("numberOfExecutions", currentExecution);
    }
}
