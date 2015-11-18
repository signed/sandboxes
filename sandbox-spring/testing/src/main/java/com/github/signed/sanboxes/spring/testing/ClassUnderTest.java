package com.github.signed.sanboxes.spring.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassUnderTest {

    private final DependencyNotInApplicationContext dependencyNotInApplicationContext;

    @Autowired
    public ClassUnderTest(DependencyNotInApplicationContext dependencyNotInApplicationContext) {
        this.dependencyNotInApplicationContext = dependencyNotInApplicationContext;
        System.out.println("new called");
    }

    public int fetchInt(){
        return dependencyNotInApplicationContext.fetchInt();
    }
}
