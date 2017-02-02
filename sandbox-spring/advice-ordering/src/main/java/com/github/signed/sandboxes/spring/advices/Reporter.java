package com.github.signed.sandboxes.spring.advices;

public interface Reporter {

    void filterEnter();
    void filterExit();

    void controllerAdvice();

    void aspectEnter();
    void aspectExit();

    void controller();
}
