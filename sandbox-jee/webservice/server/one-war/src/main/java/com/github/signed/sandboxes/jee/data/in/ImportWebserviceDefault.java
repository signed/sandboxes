package com.github.signed.sandboxes.jee.data.in;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

import org.joda.time.LocalDate;

@Stateless
@WebService(
        portName = "ImportPort",
        serviceName = "ImportService",
        targetNamespace = "http://example.org/wsdl",
        endpointInterface = "com.github.signed.sandboxes.jee.data.in.ImportWebservice")
public class ImportWebserviceDefault implements ImportWebservice {
    @Inject
    private ImporterBean importerBean;

    @Override
    public void importDataForDay(Date day, String data) {
        System.out.println("received data import request for: " + day + " -> " + data);
        DataImportParameter parameterObject = new DataImportParameter(new LocalDate(day), data);
        importerBean.performImport(parameterObject);
    }
}
