package com.github.signed.sandbox.spring.resttemplate;

import spark.Spark;

public class SparkSupport {

    public static void sparkStop() throws InterruptedException {
        Spark.stop();
        //this is needed to make sure the thread started in stop() can run
        Thread.sleep(1000);
    }
}
