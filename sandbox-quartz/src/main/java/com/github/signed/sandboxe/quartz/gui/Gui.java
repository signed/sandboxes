
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

import static javax.swing.SwingUtilities.invokeLater;

public class Gui {

    public static void main(String[] args) throws SchedulerException {
        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

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

    private final Scheduler scheduler;
    private final JobKey jobKey;

    public Gui(Scheduler scheduler, JobKey jobKey) {
        this.scheduler = scheduler;
        this.jobKey = jobKey;
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Gui");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton periodically = new JButton("run periodically");
        JButton oneShot = new JButton("trigger by hand");
        panel.add(periodically);
        panel.add(oneShot);
        frame.getContentPane().add(panel);

        TriggerKey triggerKey = TriggerKey.triggerKey("good night trigger", "polite");
        periodically.addActionListener(new Periodically(this.scheduler, jobKey, triggerKey));

        oneShot.addActionListener(new OneShot(scheduler, jobKey, triggerKey));


        frame.pack();
        frame.setVisible(true);
    }
}