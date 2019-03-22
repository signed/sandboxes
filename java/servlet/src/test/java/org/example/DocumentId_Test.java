package org.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class DocumentId_Test {

    @Test
    public void padMissingCharactersWithZero() throws Exception {
        DocumentId documentId = new DocumentId(1);

        assertThat(documentId.asString(), is("00000000000000000001"));
    }

    @Test
    public void documentIdIs20CharactersLongForMinValue() throws Exception {
        DocumentId documentId = new DocumentId(Long.MIN_VALUE);

        assertThat(documentId.asString().length(), is(20));
    }

    @Test
    public void documentIdIs20CharactersLongForMaxValue() throws Exception {
        DocumentId documentId = new DocumentId(Long.MAX_VALUE);

        assertThat(documentId.asString().length(), is(20));
    }

}