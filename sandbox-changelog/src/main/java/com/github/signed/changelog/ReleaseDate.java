package com.github.signed.changelog;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.LocalDate;

public class ReleaseDate {
    private final LocalDate day;

    public ReleaseDate(LocalDate day) {
        this.day = day;
    }

    public String asString(){
        return day.format(ISO_LOCAL_DATE);
    }
}
