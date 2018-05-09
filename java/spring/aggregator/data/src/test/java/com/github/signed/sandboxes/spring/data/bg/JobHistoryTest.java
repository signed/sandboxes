package com.github.signed.sandboxes.spring.data.bg;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class JobHistoryTest {

    @Test
    public void name() throws Exception {
        LocalDate base = LocalDate.of(2012, 1, 1);
        Job latest = jobFor(base.plusDays(3));
        Job earlieast = jobFor(base.plusDays(0));
        Job middle = jobFor(base.plusDays(1));
        JobHistory jobHistory = JobHistory.createFromUnsorted(Arrays.asList(latest, earlieast, middle));

        assertThat(jobHistory.jobAt(0), sameInstance(earlieast));
    }

    private Job jobFor(LocalDate localDate) {
        return JobBuilder.aJob().withReferenceKey(42L).created(localDate.atStartOfDay()).build();
    }
}