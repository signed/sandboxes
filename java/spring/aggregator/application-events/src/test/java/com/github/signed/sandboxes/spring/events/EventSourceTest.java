package com.github.signed.sandboxes.spring.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventSourceTest {

    @Configuration
    public static class TestConfiguration {
        @Bean
        public EventSource eventSource(ApplicationEventPublisher publisher) {
            return new EventSource(publisher);
        }

        @Bean
        public EventDrain eventDrain(){
            return new EventDrain();
        }
    }

    @Autowired
    private EventSource eventSource;

    @Autowired
    private EventDrain eventDrain;

    @Test
    public void saveAndRead() throws Exception {
        eventSource.publishBeanEvent();
    }
}
