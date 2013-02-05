package com.github.signed.sandboxe.quartz.gui.actions;

import com.github.signed.sandboxe.quartz.domain.Domain;

public class StopPeriodicalExecution implements Runnable {
    private final Domain domain;

    public StopPeriodicalExecution(Domain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        domain.stopPeriodicExecution();
    }
}