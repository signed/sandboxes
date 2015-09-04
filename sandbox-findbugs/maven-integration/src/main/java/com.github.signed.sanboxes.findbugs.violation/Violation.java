package com.github.signed.sanboxes.findbugs.violation;

public class Violation {
    private final String[] arguments;

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public Violation(String[] arguments) {
        this.arguments = arguments;
    }
}
