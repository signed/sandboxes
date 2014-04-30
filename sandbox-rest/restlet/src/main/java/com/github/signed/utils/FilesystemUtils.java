package com.github.signed.utils;

import java.io.File;

public class FilesystemUtils {
    public static File getRootOfTmpDirectory() {
        String key = "java.io.tmpdir";
        return readFileFromSystemProperties(key);
    }

    public static File getCurrentWorkingDirectory() {
        return readFileFromSystemProperties("user.dir");
    }

    private static File readFileFromSystemProperties(String key) {
        String pathToTmpDirectory = System.getProperty(key);
        return new File(pathToTmpDirectory);
    }
}