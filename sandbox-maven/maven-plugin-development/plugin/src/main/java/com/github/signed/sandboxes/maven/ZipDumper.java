package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

public class ZipDumper {

    void dumpZipContent(File file, LegalRelevantFiles legalRelevantFiles) throws ZipException {
        ZipFile zipFile = new ZipFile(file);
        List<FileHeader> fileHeaders = zipFile.getFileHeaders();

        for (FileHeader fileHeader : fileHeaders) {
            if( !fileHeader.isDirectory()) {
                String baseName = FilenameUtils.getBaseName(fileHeader.getFileName());
                if("license".equalsIgnoreCase(baseName)){
                    legalRelevantFiles.licenseFile(fileHeader);
                }else if("notice".equalsIgnoreCase(baseName)){
                    legalRelevantFiles.noticeFile(fileHeader);
                }
            }
        }
    }
}