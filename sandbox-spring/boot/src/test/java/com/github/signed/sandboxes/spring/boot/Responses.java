package com.github.signed.sandboxes.spring.boot;

import retrofit.client.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Responses {

    public static String readBodyAsUtf8String(Response response) throws IOException {
        return new String(readBytesFrom(response.getBody().in()), "UTF-8");
    }

    public static byte[] readBytesFrom(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int r = inputStream.read(buffer);
            if (r == -1) break;
            out.write(buffer, 0, r);
        }

        return out.toByteArray();
    }
}
