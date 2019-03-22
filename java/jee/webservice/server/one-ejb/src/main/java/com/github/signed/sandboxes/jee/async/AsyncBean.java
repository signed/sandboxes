package com.github.signed.sandboxes.jee.async;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncBean {

    @Asynchronous
    public Future<String> asyncMethod() {
        System.out.println("start running asyncMethod in thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished running asyncMethod in thread " + Thread.currentThread().getName());
        return new AsyncResult<>("the result");
    }
}