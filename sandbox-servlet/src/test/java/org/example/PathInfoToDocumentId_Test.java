package org.example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.google.common.base.Optional;

public class PathInfoToDocumentId_Test {

    @Test
    public void extractAValidDocumentId() throws Exception {
        Optional<DocumentId> documentId = new PathInfoToDocumentId().parseDocumentId(new DocumentId(3423L).asString());

        assertThat(documentId.get(), is(new DocumentId(3423L)));
    }

    @Test
    public void copeWithMaleFormattedDocumentIds() throws Exception {
        Optional<DocumentId> documentId = new PathInfoToDocumentId().parseDocumentId("bogus");

        assertThat(documentId.isPresent(), is(false));
    }

    @Test
    public void stripTrailingSlash() throws Exception {
        Optional<DocumentId> documentId = new PathInfoToDocumentId().parseDocumentId("/" + new DocumentId(3423L).asString());

        assertThat(documentId.get(), is(new DocumentId(3423L)));
    }
}