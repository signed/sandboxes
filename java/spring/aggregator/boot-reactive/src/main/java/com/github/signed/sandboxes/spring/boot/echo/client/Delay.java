package com.github.signed.sandboxes.spring.boot.echo.client;

public class Delay {

    public static Delay OfSeconds(int seconds) {
        return new Delay(seconds * 1000);
    }

    private final long delayInMilliseconds;

    public Delay(long delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }

    public long milliseconds() {
        return delayInMilliseconds;
    }
}
