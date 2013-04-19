package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import org.apache.commons.io.FilenameUtils;

import java.io.File;


public class SingleFileUnzip {
    private File zipFile;

    public SingleFileUnzip(File zipFile) {
        this.zipFile = zipFile;
    }

    public void unzip(FileHeader fileHeader, File extract) {
        try {
            ZipFile zipFile = new ZipFile(this.zipFile);
            zipFile.extractFile(fileHeader, extract.getAbsolutePath(), new UnzipParameters(), FilenameUtils.getName(fileHeader.getFileName()));
        } catch (ZipException e) {
            throw new RuntimeException(e);
        }
    }
}