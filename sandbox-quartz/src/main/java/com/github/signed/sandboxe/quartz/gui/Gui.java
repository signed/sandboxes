
package com.github.signed.sandboxe.quartz.gui;

/*
 * Gui.java requires no other files.
 */

import com.github.signed.sandboxe.quartz.SleepingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import static javax.swing.SwingUtilities.invokeLater;

public class Gui {

    public static void main(String[] args) throws Exception {
        final Scheduler scheduler = new JobScheduler().createCustomizedScheduler();

        final JobDetail jobDetail = JobBuilder.newJob(SleepingJob.class).withIdentity("greeting job", "polite").storeDurably().usingJobData("numberOfExecutions", 0).build();
        scheduler.addJob(jobDetail, false);
        scheduler.start();

        TriggerKey triggerKey = TriggerKey.triggerKey("good night trigger", "polite");
        final JobFacts facts = new JobFacts(jobDetail.getKey(), triggerKey);
        invokeLater(new Runnable() {
            public void run() {
                new Gui(scheduler,facts).createAndShowGUI();
            }
        });
    }

    private final Scheduler scheduler;
    private final JobFacts facts;
    private final SchedulerFacade schedulerFacade;

    public Gui(Scheduler scheduler, JobFacts facts) {
        this.scheduler = scheduler;
        this.facts = facts;
        schedulerFacade = new SchedulerFacade(scheduler);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Gui");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final TriggerKey triggerKey = facts.triggerKey;

        JPanel panel = new JPanel();

        JPanel schedulerInteraction = getPanelForSchedulerInteraction(scheduler);
        JPanel periodicallyPanel = getPanelForPeriodicPolling(triggerKey);
        JButton oneShot = new JButton("trigger by hand");

        panel.add(schedulerInteraction);
        panel.add(periodicallyPanel);
        panel.add(oneShot);

        frame.getContentPane().add(panel);

        oneShot.addActionListener(new ExecuteRunnableOnAction(new RunJobOnce(facts, schedulerFacade)));


        frame.pack();
        frame.setVisible(true);
    }

    private JPanel getPanelForSchedulerInteraction(final Scheduler scheduler) {
        JPanel schedulerInteraction = new JPanel();
        JButton listTriggerForJob = new JButton("list trigger for job");
        listTriggerForJob.addActionListener(new ExecuteRunnableOnAction(new ListKnownTriggers(scheduler, facts.jobKey)));
        JButton pauseScheduler = new JButton("pause");
        pauseScheduler.addActionListener(new ExecuteRunnableOnAction(new PauseScheduler(scheduler)));
        JButton resume = new JButton("resume");
        resume.addActionListener(new ExecuteRunnableOnAction(new ResumeAll(scheduler)));

        schedulerInteraction.add(pauseScheduler);
        schedulerInteraction.add(resume);
        schedulerInteraction.add(listTriggerForJob);
        return schedulerInteraction;
    }

    private JPanel getPanelForPeriodicPolling(TriggerKey triggerKey) {
        JPanel periodicallyPanel = new JPanel();

        JButton periodically = new JButton("run periodically");
        JButton cancelPeriodically = new JButton("cancel periodical execution");

        periodicallyPanel.add(periodically);
        periodicallyPanel.add(cancelPeriodically);
        periodically.addActionListener(new ExecuteRunnableOnAction(new RunJobPeriodically(scheduler, facts)));
        cancelPeriodically.addActionListener(new ExecuteRunnableOnAction(new StopPeriodicalExecution(triggerKey, schedulerFacade)));
        return periodicallyPanel;
    }

}