package com.github.signed.sanbox.junit;

import org.junit.Rule;
import org.junit.Test;

public class Sample {

    @Rule
    public PrintTestMethodNameRule printTestMethodNameRule = new PrintTestMethodNameRule();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        System.out.println(CaseFormat.SENTENCE.to(CaseFormat.LOWER_CAMEL, "here we go into the other direction"));
    }
}
