package com.github.signed.sandboxes.spring.advices;

public interface Reporter {

    void filterEnter();
    void filterExit();

    void earlierAdvise();
    void laterAdvise();

    void aspectEnter();

    void aspectExit();

    void controller();

    void webMvcConfigurationAdapter(String methodName);
}
