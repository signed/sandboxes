package org.example.integration;

import static org.example.integration.ResponseMatcher.responseWithStatus;
import static org.example.integration.StatusMatcher.NotFound;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("only run manually against a running server")
public class DocumentStorageService_GetTest {
    private final DocumentStorageDriver driver = new DocumentStorageDriver();

    @Test
    public void returnNotFoundForNotExistingDocument() throws Exception {

        assertThat(driver.requestNotExistingDocument(), responseWithStatus(NotFound()));
    }


}
