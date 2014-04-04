package com.github.signed.integration.camel.gui;

import org.apache.camel.CamelContext;
import org.apache.camel.VetoCamelContextStartException;
import com.github.signed.integration.camel.LifecycleStrategyAdapter;

public class CamelContextIgnition {

    private final CamelContext context;

    public CamelContextIgnition(CamelContext context, final StartStop startStop) {
        this.context = context;
        context.addLifecycleStrategy(new LifecycleStrategyAdapter() {
            @Override
            public void onContextStart(CamelContext context) throws VetoCamelContextStartException {
                startStop.displayStop();
            }

            @Override
            public void onContextStop(CamelContext context) {
                startStop.displayStart();
            }
        });
        startStop.onStart(new UserCommand() {
            @Override
            public void given() {
                start();
            }
        });

        startStop.onStop(new UserCommand() {
            @Override
            public void given() {
                stop();
            }
        });
    }

    public void start(){
        try {
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
