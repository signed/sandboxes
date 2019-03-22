package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteStreams;

public class ReadAllBytesFromInputStream {

    public ByteProcessor<byte[]> extractPayload(InputStream inputStream) throws IOException {
        ByteProcessor<byte[]> processor = new ByteProcessor<byte[]>() {
            List<Byte> readBytes = Lists.newArrayList();

            @Override
            public boolean processBytes(byte[] buf, int off, int len) throws IOException {
                for( int currentByteIndex = off; currentByteIndex <= off + len - 1; ++currentByteIndex){
                    readBytes.add(buf[currentByteIndex]);
                }
                return true;
            }

            @Override
            public byte[] getResult() {
                byte[] raw = new byte[readBytes.size()];
                for (int i = 0; i < readBytes.size(); ++i) {
                    raw[i] = readBytes.get(i);
                }
                return raw;
            }
        };
        ByteStreams.readBytes(inputStream, processor);
        return processor;
    }
}
