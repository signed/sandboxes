package com.github.signed;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ClassWithLogging {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(ClassWithLogging.class);
        Random random = new Random();
        while (true) {
            Thread.sleep(random.nextInt(10000));
            System.out.println(new DateTime());
            logger.debug("hello you fool");
        }
    }
}
