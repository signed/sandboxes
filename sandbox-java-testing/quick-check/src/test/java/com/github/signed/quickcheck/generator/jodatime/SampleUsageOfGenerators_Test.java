package com.github.signed.quickcheck.generator.jodatime;

import com.pholser.junit.quickcheck.ForAll;
import org.joda.time.LocalDate;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Theories.class)
public class SampleUsageOfGenerators_Test {

    private static int runs = 0;

    @Theory
    public void anyDayHas24Hours(@ForAll LocalDate date) throws Exception {
        assertThat(date.toInterval().toDuration().getStandardHours(), is(24l));
    }

    @Theory
    public void anyJavaDate(@ForAll(sampleSize = 100) @InTheFuture LocalDate from, @ForAll(sampleSize = 100) LocalDate until) {
        //System.out.println(runs+++": " + from + ":" + until);
    }
}