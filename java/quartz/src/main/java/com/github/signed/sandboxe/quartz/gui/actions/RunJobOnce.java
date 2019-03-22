package com.github.signed.sandboxe.quartz.gui.actions;

import com.github.signed.sandboxe.quartz.domain.Domain;

public class RunJobOnce implements Runnable {
    private Domain domain;


    public RunJobOnce(Domain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        Integer result = domain.getResultOfCurrentExecution();
        System.out.println("last execution was " + result);
    }


}