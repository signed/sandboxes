package com.github.signed.changelog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import org.junit.Test;

public class ReleaseDate_Test {

    @Test
    public void format_release_date_as_year_month_dayofmonth() throws Exception {
        ReleaseDate releaseDate = new ReleaseDate(LocalDate.of(2015, 12, 3));

        assertThat(releaseDate.asString(), is("2015-12-03"));
    }
}