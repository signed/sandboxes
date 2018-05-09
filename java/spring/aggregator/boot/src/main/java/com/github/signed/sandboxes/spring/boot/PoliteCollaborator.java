package com.github.signed.sandboxes.spring.boot;

public class PoliteCollaborator implements Collaborator {
    @Override
    public String message() {
        return "Hello kind person";
    }
}
