package com.github.signed.sandboxe.quartz.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecuteRunnableOnAction implements ActionListener {
    private final Runnable runnable;

    public ExecuteRunnableOnAction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(runnable).start();
    }
}