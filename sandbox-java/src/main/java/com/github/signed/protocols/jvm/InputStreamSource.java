package com.github.signed.protocols.jvm;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface InputStreamSource {
    InputStream inputStream() throws FileNotFoundException;
}
