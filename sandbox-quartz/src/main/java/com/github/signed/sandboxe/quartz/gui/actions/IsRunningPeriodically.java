package com.github.signed.sandboxe.quartz.gui.actions;

import com.github.signed.sandboxe.quartz.domain.Domain;

public class IsRunningPeriodically implements Runnable{

    private final Domain domain;

    public IsRunningPeriodically(Domain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        System.out.println(String.format("is running periodically: %s", domain.isRunningPeriodically()));
    }
}
