package com.github.signed.sandboxes.spring.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventSource {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public EventSource(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishBeanEvent(){
        publisher.publishEvent(new BeanEventClass());
    }

}
