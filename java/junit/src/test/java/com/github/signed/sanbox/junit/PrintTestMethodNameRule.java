package com.github.signed.sanbox.junit;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static com.github.signed.sanbox.junit.SampleTest.DEMO_OUTPUT;

public class PrintTestMethodNameRule extends TestWatcher {

    @Override
    protected void starting(Description description) {
        Logger logger = Logger.getLogger(DEMO_OUTPUT);
        logger.warn(CaseFormat.LOWER_CAMEL.to(CaseFormat.SENTENCE, description.getMethodName()));
    }
}
