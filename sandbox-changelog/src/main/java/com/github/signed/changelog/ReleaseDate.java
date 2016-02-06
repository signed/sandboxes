package com.github.signed.changelog;

import static java.time.LocalDate.of;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.LocalDate;

public class ReleaseDate {
    public static ReleaseDate Date(int year, int month, int dayOfMonth) {
        return new ReleaseDate(of(year, month, dayOfMonth));
    }

    private final LocalDate day;

    public ReleaseDate(LocalDate day) {
        this.day = day;
    }

    public String asString(){
        return day.format(ISO_LOCAL_DATE);
    }
}
