package com.github.signed.integration.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.component.file.remote.FtpComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FtpUpload_Test {

    private final CamelContext context = new DefaultCamelContext();

    @Before
    public void setUp() throws Exception {
        context.addComponent("ftp", new FtpComponent(context));
        context.start();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Test
    public void testName() throws Exception {

    }
}
