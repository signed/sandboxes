package com.github.signed.sandboxes.spring.beanvalidation;

import javax.validation.constraints.NotNull;

public class BeanToValidate {
    @NotNull(message = "do not touch this")
    public String name;
}
