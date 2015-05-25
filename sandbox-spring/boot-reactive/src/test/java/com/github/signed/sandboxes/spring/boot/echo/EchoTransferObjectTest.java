package com.github.signed.sandboxes.spring.boot.echo;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EchoTransferObjectTest {
    @Test
    public void testName() throws Exception {
        EchoTransferObject to = new EchoTransferObject();
        to.responseDelayInMilliseconds = 2000;
        to.message = "the first";
        String json = new ObjectMapper().writeValueAsString(to);
        System.out.println(json);
    }
}