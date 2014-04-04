package com.github.signed.integration.camel.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.github.signed.integration.camel.TemplateTrigger;
import com.github.signed.integration.camel.gui.UserCommand;

public class TemplateTriggerSwing implements TemplateTrigger {
    private final JPanel panel = new JPanel();
    private final JButton trigger = new JButton("trigger template");

    public TemplateTriggerSwing() {
        panel.add(trigger);
    }

    @Override
    public void onTrigger(final UserCommand command) {
        trigger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        command.given();
                        return null;
                    }
                }.execute();
            }
        });
    }

    public void addTo(JPanel parent) {
        parent.add(panel);
    }
}
