package org.example;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.example.stubs.ServletRequestForTest;
import org.junit.Test;

public class DocumentStorageService_GetTest {

    @Test
    public void return404ForInvalidDocumentIds() throws Exception {
        ServletRequestForTest request = new ServletRequestForTest("");
        request.setPathInfo("bogus");
        HttpServletResponse response = mock(HttpServletResponse.class);
        new DocumentStorageService(new ReadAllBytesFromInputStream(), new DocumentStorage(new DocumentIdGenerator()), new PathInfoToDocumentId()).doGet(request, response);

        verify(response).setStatus(404);
    }
}