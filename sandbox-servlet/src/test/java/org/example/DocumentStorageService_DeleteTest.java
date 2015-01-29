package org.example;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.example.stubs.ServletRequestForTest;
import org.junit.Test;

public class DocumentStorageService_DeleteTest {
    private final ServletRequestForTest request = new ServletRequestForTest("");
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    public void return404ForInvalidDocumentIds() throws Exception {
        sendRequestToPath("bogus");

        verify(response).setStatus(404);
    }

    private void sendRequestToPath(String pathInfo) throws ServletException, IOException {
        request.setPathInfo(pathInfo);
        new DocumentStorageService(new ReadAllBytesFromInputStream(), new DocumentStorage(new DocumentIdGenerator()), new PathInfoToDocumentId()).doDelete(request, response);
    }
}