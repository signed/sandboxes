package org.example;

import com.google.common.base.Optional;

public class PathInfoToDocumentId {

    public Optional<DocumentId> parseDocumentId(String pathInfo) {
        try{
            long value = Long.parseLong(pathInfo.substring(1, pathInfo.length()));
            return Optional.of(new DocumentId(value));
        }catch (Exception ex){
            return Optional.absent();
        }
    }
}
