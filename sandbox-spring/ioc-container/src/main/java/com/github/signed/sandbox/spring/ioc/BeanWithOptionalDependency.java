package com.github.signed.sandbox.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;

public class BeanWithOptionalDependency {

    @Autowired
    private RequiredDependency required;

    @Autowired(required = false)
    private OptionalDependency optional;


}
