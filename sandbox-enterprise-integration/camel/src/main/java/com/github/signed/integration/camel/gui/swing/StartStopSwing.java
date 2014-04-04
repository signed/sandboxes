package com.github.signed.integration.camel.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.signed.integration.camel.gui.StartStop;
import com.github.signed.integration.camel.gui.UserCommand;

public class StartStopSwing implements StartStop {
    private final JPanel panel = new JPanel();
    private final JButton start = new JButton("start");
    private final JButton stop = new JButton("stop");

    public StartStopSwing() {
        panel.add(start);
        panel.add(stop);
        hideStop();
    }

    @Override
    public void displayStart() {
        hideStop();
        start.setVisible(true);
    }

    @Override
    public void displayStop() {
        hideStart();
        stop.setVisible(true);
    }

    @Override
    public void onStart(final UserCommand command) {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.given();
            }
        });

    }

    @Override
    public void onStop(final UserCommand command) {
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.given();
            }
        });
    }

    public void addTo(JPanel parent) {
        parent.add(this.panel);
    }

    private void hideStart() {
        start.setVisible(false);
    }

    private void hideStop() {
        stop.setVisible(false);
    }
}
