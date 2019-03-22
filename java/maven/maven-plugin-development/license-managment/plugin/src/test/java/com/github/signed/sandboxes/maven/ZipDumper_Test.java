package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ZipDumper_Test {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void findLicenseFilesIgnoringDepthAndSpellingAndFileExtension() throws Exception {
        File some = folder.newFolder("some/");
        folder.newFolder("some/directory/");
        folder.newFile("some/directory/liCense.txt");
        File createdZipFile = new File(folder.getRoot(), "sample.zip");
        ZipFile zipFile = new ZipFile(createdZipFile);
        zipFile.addFolder(some, new ZipParameters());

        ZipDumper dumper = new ZipDumper();
        final FileHeader[] found = new FileHeader[1];
        dumper.dumpZipContent(createdZipFile, new LegalRelevantFiles() {
            @Override
            public void licenseFile(FileHeader license) {
                found[0] = license;
            }

            @Override
            public void noticeFile(FileHeader notice) {
                throw new RuntimeException("should not be called");
            }
        });

        assertThat(found[0].getFileName(), is("some/directory/liCense.txt"));
    }
}
