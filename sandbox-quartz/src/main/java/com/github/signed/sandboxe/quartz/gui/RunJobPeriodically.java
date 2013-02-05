package com.github.signed.sandboxe.quartz.gui;

import com.github.signed.sandboxe.quartz.domain.Domain;

public class RunJobPeriodically implements Runnable {
    private final Domain domain;

    public RunJobPeriodically(Domain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        domain.runPeriodically();
    }
}