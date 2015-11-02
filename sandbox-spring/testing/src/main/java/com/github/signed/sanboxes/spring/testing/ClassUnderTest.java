package com.github.signed.sanboxes.spring.testing;

import org.springframework.stereotype.Component;

@Component
public class ClassUnderTest {

    public ClassUnderTest() {
    }

    public int fetchInt(){
        return 7;
    }
}
