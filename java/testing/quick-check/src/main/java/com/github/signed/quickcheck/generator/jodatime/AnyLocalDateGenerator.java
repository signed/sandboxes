package com.github.signed.quickcheck.generator.jodatime;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.java.util.DateGenerator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class AnyLocalDateGenerator extends Generator<LocalDate> {

    private final DateGenerator dateGenerator = new DateGenerator();

    public AnyLocalDateGenerator() {
        super(LocalDate.class);
    }

    public void configure(InTheFuture inTheFuture){

    }

    public void configure(InThePast inThePast){

    }

    public void configure(Today today){

    }

    public void configure(TimeChangeDate timeChangeDate){

    }

    public void configure(InRange inRange){
        dateGenerator.configure(inRange);
    }

    @Override
    public LocalDate generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        return new DateTime(dateGenerator.generate(sourceOfRandomness, generationStatus)).toLocalDate();
    }
}
