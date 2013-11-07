package com.github.signed.quickcheck.generator.jodatime;

import com.pholser.junit.quickcheck.generator.GeneratorConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@GeneratorConfiguration
public @interface TimeChangeDate {
    // ...
}