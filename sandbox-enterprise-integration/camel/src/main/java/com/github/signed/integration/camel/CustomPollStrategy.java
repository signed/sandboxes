package com.github.signed.integration.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.component.file.remote.RemoteFilePollingConsumerPollStrategy;

public class CustomPollStrategy implements org.apache.camel.spi.PollingConsumerPollStrategy {

    private final RemoteFilePollingConsumerPollStrategy wrapped = new RemoteFilePollingConsumerPollStrategy();

    @Override
    public boolean begin(Consumer consumer, Endpoint endpoint) {
        return wrapped.begin(consumer, endpoint);
    }

    @Override
    public void commit(Consumer consumer, Endpoint endpoint, int polledMessages) {
        wrapped.commit(consumer, endpoint, polledMessages);
    }

    @Override
    public boolean rollback(Consumer consumer, Endpoint endpoint, int retryCounter, Exception cause) throws Exception {
        return wrapped.rollback(consumer, endpoint, retryCounter, cause);
    }
}
