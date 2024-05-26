package com.github.signed.quickcheck.generator.jodatime;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class German25_Hour_TimeChangeDate {

    public LocalDate in(int year) {
        LocalDate localDate = new LocalDate(year, 11, 1).minusDays(1);
        while(localDate.getDayOfWeek() != DateTimeConstants.SUNDAY){
            localDate = localDate.minusDays(1);
        }

        return localDate;
    }
}
