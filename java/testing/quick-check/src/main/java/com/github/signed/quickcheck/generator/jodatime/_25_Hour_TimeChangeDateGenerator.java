package com.github.signed.quickcheck.generator.jodatime;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import org.joda.time.LocalDate;

public class _25_Hour_TimeChangeDateGenerator extends Generator<LocalDate>{

    public _25_Hour_TimeChangeDateGenerator() {
        super(LocalDate.class);
        System.out.println("instantiate switch day");
    }

    @Override
    public LocalDate generate(SourceOfRandomness random, GenerationStatus status) {
        int minYear = 2002;
        int maxYear = 3000;
        return new German25_Hour_TimeChangeDate().in(random.nextInt(minYear, maxYear));
    }
}
