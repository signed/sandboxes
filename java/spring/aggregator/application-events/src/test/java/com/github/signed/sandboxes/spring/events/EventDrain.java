package com.github.signed.sandboxes.spring.events;

import org.springframework.context.event.EventListener;

public class EventDrain {

    @EventListener
    public void onBeanEvent(BeanEventClass bean) {
        System.out.println(bean);
    }
}
