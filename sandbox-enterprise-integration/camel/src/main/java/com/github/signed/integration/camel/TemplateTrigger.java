package com.github.signed.integration.camel;

import com.github.signed.integration.camel.gui.UserCommand;

public interface TemplateTrigger {

    void  onTrigger(UserCommand command);
}
