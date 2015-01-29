package org.example;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.example.stubs.InMemoryServletOutputStream;
import org.example.stubs.ServletRequestForTest;
import org.junit.Test;

public class DocumentStorageService_PostTest {

    @Test
    public void setEncodingInResult() throws Exception {
        DocumentStorageService service = new DocumentStorageService(new ReadAllBytesFromInputStream(), new DocumentStorage(new DocumentIdGenerator()), new PathInfoToDocumentId());
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ServletOutputStream servletOutputStream = new InMemoryServletOutputStream(outputStream);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        service.doPost(new ServletRequestForTest("content"), response);

        verify(response).setStatus(201);
    }


}