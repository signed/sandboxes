package com.github.signed.pmd.abstractions;

import com.beust.jcommander.internal.Maps;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class InMemoryCodeWriter extends CodeWriter {

    private final Map<String, ByteArrayOutputStream> remember = Maps.newHashMap();
    private final Map<String, String> sourceCodeByClassName = Maps.newHashMap();


    public InMemoryCodeWriter() {
        encoding = "UTF-8";
    }

    @Override
    public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
        ByteArrayOutputStream current = new ByteArrayOutputStream();
        remember.put(fileName, current);

        return current;
    }

    @Override
    public void close() throws IOException {
        for (Map.Entry<String, ByteArrayOutputStream> entry : remember.entrySet()) {
            ByteArrayOutputStream value = entry.getValue();
            sourceCodeByClassName.put(entry.getKey(), value.toString(this.encoding));
            value.close();
        }
        remember.clear();
    }

    public String getSourceForClass(String className) {
        return sourceCodeByClassName.get(className + ".java");
    }
}
