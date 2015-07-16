package com.github.signed.sandboxes.spring.boot.caller.server;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.signed.sandboxes.spring.boot.caller.api.TransferObject;
import com.github.signed.sandboxes.spring.boot.echo.api.EchoTransferObject;
import com.github.signed.sandboxes.spring.boot.echo.client.Delay;
import com.github.signed.sandboxes.spring.boot.echo.client.DelayEchoServiceClient;
import com.github.signed.sandboxes.spring.boot.echo.client.Message;

import rx.Observable;


@RestController
@EnableAutoConfiguration
public class SampleController {

    private final DelayEchoServiceClient client = new DelayEchoServiceClient();

    @RequestMapping("/")
    @ResponseBody
    public ListenableFuture<TransferObject> home() {
        SettableListenableFuture<TransferObject> result = new SettableListenableFuture<>();
        Observable<ResponseEntity<EchoTransferObject>> firstObservable = client.callWith(Delay.OfSeconds(2), Message.WithText("first"));
        Observable<ResponseEntity<EchoTransferObject>> secondObservable = client.callWith(Delay.OfSeconds(4), Message.WithText("second"));

        Observable<TransferObject> zipped = Observable.zip(firstObservable, secondObservable, (first, second) -> {
            EchoTransferObject firstResponse = first.getBody();
            EchoTransferObject secondResponse = second.getBody();
            TransferObject transferObject = new TransferObject();
            LocalDateTime now = LocalDateTime.now();
            transferObject.content = String.format("%s->%s|%s", now, firstResponse.message, secondResponse.message);
            return transferObject;
        });
        zipped.subscribe(result::set, result::setException);
        return result;
    }
}
