package org.example;

public class Document {
    private final byte[] content;

    public Document(byte[] content){
        this.content = content;
    }

    public byte[] getContent(){
        return content;
    }

}
