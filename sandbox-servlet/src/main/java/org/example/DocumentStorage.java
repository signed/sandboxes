package org.example;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class DocumentStorage {

    private static final Map<DocumentId, Document> documents = Maps.newConcurrentMap();

    private final DocumentIdGenerator generator;

    public DocumentStorage(DocumentIdGenerator generator) {
        this.generator = generator;
    }

    public DocumentId create(Document document) {
        DocumentId documentId = generator.generateUniqueDocumentId();
        documents.put(documentId, document);
        return documentId;
    }

    public Optional<Document> getDocumentFor(DocumentId documentId){
        Document document = documents.get(documentId);
        return Optional.fromNullable(document);
    }

    public Optional<Document> update(DocumentId documentId, Document document) {
        Document old = documents.put(documentId, document);
        return Optional.fromNullable(old);
    }

    public Optional<Document> deleteDocumentWith(DocumentId documentId) {
        Document removed = documents.remove(documentId);
        return Optional.fromNullable(removed);
    }

    public boolean contains(DocumentId documentId) {
        return documents.containsKey(documentId);
    }
}
