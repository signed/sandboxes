package com.github.signed.integration.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import com.github.signed.integration.camel.gui.CamelContextIgnition;
import com.github.signed.integration.camel.gui.swing.CamelEvaluationCenterSwing;

public class CamelEvaluationCenter {

    public static void main(String[] args) {
        CamelEvaluationCenterSwing gui = new CamelEvaluationCenterSwing();
        gui.constructApplicationFrame();

        CamelContext context = new DefaultCamelContext();
        CamelContextIgnition camelContextIgnition = new CamelContextIgnition(context, gui.startStop());
        CamelEvaluationCenter camelEvaluationCenter = new CamelEvaluationCenter();
        gui.start();
    }

}
