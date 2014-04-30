package com.github.signed.tryanderror.jersey.resources;

public class ProcessInput {
    private final long numberOfIterations;

    public ProcessInput(long numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public String toString() {
        return "#iterations: "+numberOfIterations;
    }
}
