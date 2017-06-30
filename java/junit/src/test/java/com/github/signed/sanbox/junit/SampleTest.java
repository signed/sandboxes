package com.github.signed.sanbox.junit;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;

public class SampleTest {

    static final String DEMO_OUTPUT = "demo-output";

    @Rule
    public PrintTestMethodNameRule printTestMethodNameRule = new PrintTestMethodNameRule();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        Logger logger = Logger.getLogger(DEMO_OUTPUT);

        String message = CaseFormat.SENTENCE.to(CaseFormat.LOWER_CAMEL, "here we go into the other direction");
        logger.warn(message);

        String json = "{\n" +
                "  \"one\": 34,\n" +
                "  \"two\": \"banana\",\n" +
                "  \"deep\": {\n" +
                "    \"three\": \"mountains\",\n" +
                "    \"four\": null\n" +
                "  }\n" +
                "}";
        logger.warn(json);
    }
}
