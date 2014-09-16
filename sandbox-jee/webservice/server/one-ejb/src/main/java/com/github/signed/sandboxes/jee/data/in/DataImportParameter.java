package com.github.signed.sandboxes.jee.data.in;

import org.joda.time.LocalDate;

public class DataImportParameter {
    private final LocalDate day;
    private final String data;

    public DataImportParameter(LocalDate day, String data) {
        this.day = day;
        this.data = data;
    }

    public String data() {
        return data;
    }

    public LocalDate day() {
        return day;
    }
}
