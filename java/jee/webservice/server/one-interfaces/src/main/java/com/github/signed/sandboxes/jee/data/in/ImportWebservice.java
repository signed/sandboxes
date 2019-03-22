package com.github.signed.sandboxes.jee.data.in;

import java.util.Date;

import javax.jws.WebService;

@WebService(targetNamespace = "http://example.org/wsdl")
public interface ImportWebservice {
    void importDataForDay(Date day, String data);
}
