package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.model.FileHeader;

public interface LegalRelevantFiles {
    void licenseFile(FileHeader license);

    void noticeFile(FileHeader notice);
}
