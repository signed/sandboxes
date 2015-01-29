package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class DocumentIdGenerator {
    private static final AtomicLong number = new AtomicLong(0L);

    public DocumentId generateUniqueDocumentId(){
        return new DocumentId(number.getAndIncrement());
    }
}
