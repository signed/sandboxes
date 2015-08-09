package com.github.signed.sandboxes.spring.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Test;

public class ValidationInIsolationTest {

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testLicensePlateNotUpperCase() {
        BeanToValidate beanToValidate = new BeanToValidate();

        Set<ConstraintViolation<BeanToValidate>> constraintViolations = validator.validate(beanToValidate);
        assertThat(constraintViolations, Matchers.hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), is("do not touch this"));
    }
}