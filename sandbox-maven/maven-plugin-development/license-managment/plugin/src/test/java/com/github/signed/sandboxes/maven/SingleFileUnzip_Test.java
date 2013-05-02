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

public class SingleFileUnzip_Test {


    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testName() throws Exception {
        File source = folder.newFolder("source");
        File extract = folder.newFolder("extract");
        File sub = folder.newFolder("source/sub");
        folder.newFile("source/sub/LicEnse");
        File sampleZipFile = new File(folder.getRoot(), "sample.zip");
        ZipFile zip = new ZipFile(sampleZipFile);
        zip.addFolder(sub, new ZipParameters());

        FileHeader fileHeader = zip.getFileHeader("sub/LicEnse");

        new SingleFileUnzip(sampleZipFile).unzip(fileHeader, extract);

        assertThat(new File(extract, "LicEnse").exists(), is(true));
    }
}
