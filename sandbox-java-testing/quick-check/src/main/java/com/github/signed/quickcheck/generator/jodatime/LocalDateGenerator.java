package com.github.signed.quickcheck.generator.jodatime;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import org.joda.time.LocalDate;

public class LocalDateGenerator extends Generator<LocalDate> {

    public LocalDateGenerator() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {

        return new LocalDate(323l);
    }
}
