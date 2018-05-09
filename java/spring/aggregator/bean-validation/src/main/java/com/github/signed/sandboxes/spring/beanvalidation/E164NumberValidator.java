package com.github.signed.sandboxes.spring.beanvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class E164NumberValidator implements ConstraintValidator<E164Number, PhoneNumberTO> {

    @Override
    public void initialize(E164Number annotation) {

    }

    @Override
    public boolean isValid(PhoneNumberTO phoneNumberTO, ConstraintValidatorContext context) {
        return false;
    }
}
