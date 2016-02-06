package com.github.signed.changelog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Resources {
    public static String readAsString(String name, Charset charset) throws IOException {
        try (InputStream in = ClassLoader.getSystemResourceAsStream(name)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int result = 0;
            while (-1 != result) {
                result = in.read(buffer);
                if (-1 != result) {
                    out.write(buffer, 0, result);
                }
            }
            return new String(out.toByteArray(), charset);
        }
    }
}
