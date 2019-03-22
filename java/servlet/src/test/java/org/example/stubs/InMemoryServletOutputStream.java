package org.example.stubs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class InMemoryServletOutputStream extends ServletOutputStream {
    private final ByteArrayOutputStream outputStream;

    public InMemoryServletOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public boolean isReady() {
        throw new RuntimeException("isReady not implemented");
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new RuntimeException("setWriteListener not implemented");
    }
}
