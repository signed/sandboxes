package com.github.signed.sanbox.junit;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class PrintTestMethodNameRule extends TestWatcher {

    @Override
    protected void starting(Description description) {
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.SENTENCE, description.getMethodName()));
    }
}
