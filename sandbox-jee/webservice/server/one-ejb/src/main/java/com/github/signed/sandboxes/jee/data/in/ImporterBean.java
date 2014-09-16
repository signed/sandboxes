package com.github.signed.sandboxes.jee.data.in;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class ImporterBean {

    @Asynchronous
    public void performImport(DataImportParameter parameter) {

        String data = parameter.data();
        System.out.println("Starting to import: " + data);
        try {
            Thread.sleep(data.length()*250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done importing: " + data);
    }
}
