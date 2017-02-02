package com.github.signed.sandboxes.spring.advices;

public interface Reporter {

    void filterEnter();
    void filterExit();

    void earlierAdvise(String exceptionClassName);
    void laterAdvise(String exceptionClassName);

    void aspectEnter();

    void aspectExit();

    void controller();

    void webMvcConfigurationAdapter(String methodName);
}
