package com.github.signed.sandboxes.spring.testing;

import org.springframework.stereotype.Component;

@Component
public class DependencyNotInApplicationContext {
    public int fetchInt() {
        return 45;
    }
}
