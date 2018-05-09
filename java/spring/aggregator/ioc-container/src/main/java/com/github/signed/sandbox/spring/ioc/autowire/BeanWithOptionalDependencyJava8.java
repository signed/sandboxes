package com.github.signed.sandbox.spring.ioc.autowire;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanWithOptionalDependencyJava8 {

    private final RequiredDependency required;
    private final OptionalDependency optional;


    @Autowired
    public BeanWithOptionalDependencyJava8(RequiredDependency requiredDependency, Optional<OptionalDependency> maybeOptionalDependency) {
        this.required = requiredDependency;
        this.optional = maybeOptionalDependency.orElse(null);
    }

    public RequiredDependency requiredDependency() {
        return required;
    }

    public boolean optionalBeanPresent() {
        return null != optional;
    }
}
