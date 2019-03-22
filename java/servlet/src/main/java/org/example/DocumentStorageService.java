package org.example;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Optional;
import com.google.common.io.ByteProcessor;

public class DocumentStorageService extends HttpServlet {
    private final DocumentStorage storage;
    private final ReadAllBytesFromInputStream byteReader;
    private PathInfoToDocumentId pathInfoToDocumentId;

    public DocumentStorageService(){
        this(new ReadAllBytesFromInputStream(), new DocumentStorage(new DocumentIdGenerator()), new PathInfoToDocumentId());
    }

    public DocumentStorageService(ReadAllBytesFromInputStream reader, DocumentStorage storage, PathInfoToDocumentId pathInfoToDocumentId) {
        this.byteReader = reader;
        this.storage = storage;
        this.pathInfoToDocumentId = pathInfoToDocumentId;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document document = extractDocumentFromRequest(request);

        DocumentId documentId = storage.create(document);
        String encoding = "us-ascii";
        byte[] bytes = documentId.asString().getBytes(Charset.forName(encoding));

        response.setContentType("text/plain");
        response.setCharacterEncoding(encoding);
        response.setContentLength(bytes.length);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getOutputStream().write(bytes);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<DocumentId> documentId = extractValidDocumentIdOrHandleSetErrorRespsonse(request, response);

        if (!documentId.isPresent()) {
            return;
        }

        Optional<Document> maybeADocument = storage.getDocumentFor(documentId.get());
        if (!maybeADocument.isPresent()) {
            respondWithNotFound(response);
            return;
        }

        response.getOutputStream().write(maybeADocument.get().getContent());
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<DocumentId> documentId = extractValidDocumentIdOrHandleSetErrorRespsonse(request, response);

        if (!documentId.isPresent()) {
            return;
        }

        if (!storage.contains(documentId.get())) {
            respondWithNotFound(response);
            return;
        }

        Document document = extractDocumentFromRequest(request);
        storage.update(documentId.get(), document);
        respondWithNoContent(response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<DocumentId> documentId = extractValidDocumentIdOrHandleSetErrorRespsonse(request, response);

        if (!documentId.isPresent()) {
            return;
        }

        Optional<Document> documentOptional = storage.deleteDocumentWith(documentId.get());
        if (!documentOptional.isPresent()) {
            respondWithNotFound(response);
            return;
        }

        respondWithNoContent(response);
    }

    private Document extractDocumentFromRequest(HttpServletRequest request) throws IOException {
        ByteProcessor<byte[]> processor = byteReader.extractPayload(request.getInputStream());
        return new Document(processor.getResult());
    }

    private Optional<DocumentId> extractValidDocumentIdOrHandleSetErrorRespsonse(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        Optional<DocumentId> documentId = pathInfoToDocumentId.parseDocumentId(pathInfo);

        if (!documentId.isPresent()) {
            respondWithNotFound(response);
        }
        return documentId;
    }

    private void respondWithNotFound(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void respondWithNoContent(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}