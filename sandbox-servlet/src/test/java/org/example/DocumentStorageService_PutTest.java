package org.example;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.example.stubs.ServletRequestForTest;
import org.junit.Test;

public class DocumentStorageService_PutTest {
    private final ServletRequestForTest request = new ServletRequestForTest("");
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private DocumentStorage storage = new DocumentStorage(new DocumentIdGenerator());

    @Test
    public void return404ForInvalidDocumentIds() throws Exception {
        sendRequestToPath("bogus");

        verify(response).setStatus(404);
    }

    @Test
    public void return404NoContentWasFound() throws Exception {
        sendRequestToPath(new DocumentId(5).asString());

        verify(response).setStatus(404);
    }

    @Test
    public void return204NoContentOnSuccess() throws Exception {
        DocumentId documentId = new DocumentId(5);
        storage.update(documentId, new Document("do not care".getBytes()));
        sendRequestToPath(documentId.asString());

        verify(response).setStatus(204);
    }

    private void sendRequestToPath(String pathInfo) throws ServletException, IOException {
        request.setPathInfo(pathInfo);
        new DocumentStorageService(new ReadAllBytesFromInputStream(), storage, new PathInfoToDocumentId()).doPut(request, response);
    }
}