package com.github.signed.sanboxes.spring.events;

import com.github.signed.sandboxes.spring.events.BeanEventClass;
import org.springframework.context.event.EventListener;

public class EventDrain {

    @EventListener
    public void onBeanEvent(BeanEventClass bean) {
        System.out.println(bean);
    }
}
