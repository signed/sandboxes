package org.example;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DocumentIdGenerator_Test {

    @Test
    public void incrementAfterEachGeneration() throws Exception {
        DocumentIdGenerator documentIdGenerator = new DocumentIdGenerator();
        DocumentId first = documentIdGenerator.generateUniqueDocumentId();
        DocumentId second = documentIdGenerator.generateUniqueDocumentId();

        assertThat(first.asString(), not(second.asString()));
    }
}