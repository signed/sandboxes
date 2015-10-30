package com.github.signed.sandbox.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanWithOptionalDependency {

    @Autowired
    private RequiredDependency required;

    @Autowired(required = false)
    private OptionalDependency optional;

    public RequiredDependency requiredDependency() {
        return required;
    }

    public boolean optionalBeanPresent() {
        return null != optional;
    }
}
