package org.example.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("only run manually against a running server")
public class DocumentStorageService_PostTest {
    private final DocumentStorageDriver driver = new DocumentStorageDriver();

    @Test
    public void returnDocumentIdOfLength20() throws Exception {
        Response response = driver.uploadDocumentWithContent("content");
        String documentId = response.readEntity(String.class);
        response.close();

        assertThat(documentId.length(), is(20));
    }


}
