package com.github.signed.cucumber;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;
import org.joda.time.LocalDate;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Theories.class)
public class Hello_Test {

    @Theory public void anyIntegerIsGreaterThanSeven(@ForAll int value) throws Exception {
        assertThat(value, greaterThan(7));
    }

    @Theory public void anyDayHas24Hours(@ForAll LocalDate date) throws Exception{
        assertThat(date.toInterval().toDuration().getStandardHours(), is(24l));
    }

    @Theory public void anyJavaDate(@ForAll @InRange(min="", max="") Date date){

    }
}