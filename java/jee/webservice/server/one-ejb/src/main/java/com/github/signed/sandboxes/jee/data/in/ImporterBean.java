package com.github.signed.sandboxes.jee.data.in;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class ImporterBean {

    @Asynchronous
    public Future<Integer> performImport(DataImportParameter parameter) {

        String data = parameter.data();
        System.out.println("Starting to import: " + data);
        int length = data.length();
        try {
            Thread.sleep(length * 250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done importing: " + data);
        return new AsyncResult<>(length);
    }
}
