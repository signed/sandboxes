package com.github.signed.sandboxes.jee.async;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncBean {
 
    @Asynchronous
    public void asyncMethod() {
        System.out.println("start running asyncMethod in thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {  e.printStackTrace();  }
        System.out.println("finished running asyncMethod in thread " + Thread.currentThread().getName());
    }
}