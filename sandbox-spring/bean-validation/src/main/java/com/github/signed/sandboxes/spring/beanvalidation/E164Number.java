package com.github.signed.sandboxes.spring.beanvalidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = E164NumberValidator.class)
public @interface E164Number {

    String message() default "{com.github.signed.spring.beanvalidation.E164NumberValidator.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}