package com.github.signed.sandboxes.jee.data.in;

import javax.ejb.Local;

@Local
public interface ImportSerializer {
    void putInImportQueue(DataImportParameter parameterObject);
}
