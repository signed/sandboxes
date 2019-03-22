package com.github.signed.tryanderror.rest;

import com.github.signed.utils.FilesystemUtils;
import org.junit.Test;

import java.io.File;

public class RestServerTest {

    @Test
    public void testName() throws Exception {
        File pathToTmpDirectory = FilesystemUtils.getRootOfTmpDirectory();
        System.out.println("OS current temporary directory is "                        + pathToTmpDirectory);
    }
}