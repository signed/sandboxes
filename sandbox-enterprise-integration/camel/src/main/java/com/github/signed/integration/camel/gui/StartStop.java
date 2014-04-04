package com.github.signed.integration.camel.gui;

public interface StartStop {
    void displayStart();
    void displayStop();

    void onStart(UserCommand command);

    void onStop(UserCommand command);
}
