package com.github.signed.sandboxes.spring.boot.echo.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import com.github.signed.sandboxes.spring.boot.echo.api.EchoTransferObject;
import com.github.signed.sandboxes.spring.boot.echo.server.EmbeddedServletContainer;

import rx.Observable;

public class DelayEchoServiceClient {
    public Observable<ResponseEntity<EchoTransferObject>> callWith(Delay delay, Message message) {
        AsyncRestTemplate rest = new AsyncRestTemplate();
        ListenableFuture<ResponseEntity<EchoTransferObject>> first = rest.postForEntity("http://localhost:{port}/", echoTransferObject(delay, message), EchoTransferObject.class, EmbeddedServletContainer.PORT);
        return Observable.from(first);
    }

    private HttpEntity<EchoTransferObject> echoTransferObject(Delay delay, Message message) {
        EchoTransferObject requestObject = new EchoTransferObject();
        requestObject.message = message.text();
        requestObject.responseDelayInMilliseconds = delay.milliseconds();
        return new HttpEntity<>(requestObject);
    }
}
