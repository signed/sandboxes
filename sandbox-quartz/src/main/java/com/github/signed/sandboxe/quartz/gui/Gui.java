
package com.github.signed.sandboxe.quartz.gui;

/*
 * Gui.java requires no other files.
 */

import com.github.signed.sandboxe.quartz.SleepingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.util.Properties;

import static javax.swing.SwingUtilities.invokeLater;

public class Gui {

    public static void main(String[] args) throws Exception {
        final Scheduler scheduler = createCustomizedScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SleepingJob.class).withIdentity("greeting job", "polite").storeDurably().usingJobData("numberOfExecutions", 0).build();
        scheduler.addJob(jobDetail, false);
        scheduler.start();
        final JobKey jobKey = jobDetail.getKey();
        invokeLater(new Runnable() {
            public void run() {
                new Gui(scheduler, jobKey).createAndShowGUI();
            }
        });
    }

    private static Scheduler createCustomizedScheduler() throws IOException, SchedulerException {
        Properties schedulerProperties = new Properties();
        schedulerProperties.load(Scheduler.class.getResourceAsStream("/org/quartz/quartz.properties"));
        schedulerProperties.put("org.quartz.jobStore.misfireThreshold", "1000");

        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(schedulerProperties);
        return factory.getScheduler();
    }

    private final Scheduler scheduler;
    private final JobKey jobKey;

    public Gui(Scheduler scheduler, JobKey jobKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Gui");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final TriggerKey triggerKey = TriggerKey.triggerKey("good night trigger", "polite");

        JPanel panel = new JPanel();

        JPanel schedulerInteraction = getPanelForSchedulerInteraction(scheduler);
        JPanel periodicallyPanel = getPanelForPeriodicPolling(triggerKey);
        JButton oneShot = new JButton("trigger by hand");

        panel.add(schedulerInteraction);
        panel.add(periodicallyPanel);
        panel.add(oneShot);

        frame.getContentPane().add(panel);

        oneShot.addActionListener(new ExecuteRunnableOnAction(new RunJobOnce(scheduler, jobKey, triggerKey)));


        frame.pack();
        frame.setVisible(true);
    }

    private JPanel getPanelForSchedulerInteraction(final Scheduler scheduler) {
        JPanel schedulerInteraction = new JPanel();
        JButton listTriggerForJob = new JButton("list trigger for job");
        listTriggerForJob.addActionListener(new ExecuteRunnableOnAction(new ListKnownTriggers(scheduler, jobKey)));
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
        periodically.addActionListener(new ExecuteRunnableOnAction(new RunJobPeriodically(scheduler, jobKey, triggerKey)));
        cancelPeriodically.addActionListener(new ExecuteRunnableOnAction(new StopPeriodicalExecution(triggerKey, scheduler)));
        return periodicallyPanel;
    }

}