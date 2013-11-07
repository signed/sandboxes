package com.github.signed.quickcheck.generator.jodatime;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class German25_Hour_TimeChangeDate_lastSundayInOctoberTest {

    @Test
    public void _2002_TheYearTheNewLawWasIntroduced(){
        assertThat(the25HourDayIn(2002), is(new LocalDate(2002, 10, 27)));
    }

    @Test
    public void _2013(){
        assertThat(the25HourDayIn(2013), is(new LocalDate(2013, 10, 27)));
    }

    private LocalDate the25HourDayIn(int year) {
        return new German25_Hour_TimeChangeDate().in(year);
    }

}
