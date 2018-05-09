package com.github.signed.sandboxes.spring.boot.caller.server;

import java.time.LocalDateTime;

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
import rx.schedulers.Schedulers;


@RestController
public class SampleController {

    private final DelayEchoServiceClient client = new DelayEchoServiceClient();

    @RequestMapping("/")
    @ResponseBody
    public ListenableFuture<TransferObject> home() {
        dumpCurrentSystemTime("start handling request");
        SettableListenableFuture<TransferObject> result = new SettableListenableFuture<>();
        Observable<ResponseEntity<EchoTransferObject>> firstObservable = client.callWith(Delay.OfSeconds(2), Message.WithText("first"));
        Observable<ResponseEntity<EchoTransferObject>> secondObservable = client.callWith(Delay.OfSeconds(4), Message.WithText("second"));

        firstObservable = firstObservable.doOnNext(ignore -> dumpCurrentSystemTime("first completed"));
        secondObservable = secondObservable.doOnNext(ignore -> dumpCurrentSystemTime("second completed"));

        Observable<TransferObject> resultObservable;
        resultObservable = Observable.combineLatest(firstObservable, secondObservable, this::constructResponse);

        resultObservable.subscribeOn(Schedulers.computation()).doOnNext(dudu -> dumpCurrentSystemTime("combined both results")).subscribe(result::set, result::setException);
        dumpCurrentSystemTime("leaving controller method");
        return result;
    }

    private TransferObject constructResponse(ResponseEntity<EchoTransferObject> first, ResponseEntity<EchoTransferObject> second) {
        EchoTransferObject firstResponse = first.getBody();
        EchoTransferObject secondResponse = second.getBody();
        TransferObject transferObject = new TransferObject();
        LocalDateTime now = LocalDateTime.now();
        transferObject.content = String.format("%s->%s|%s", now, firstResponse.message, secondResponse.message);
        return transferObject;
    }

    private void dumpCurrentSystemTime(String information) {
        System.out.println(LocalDateTime.now() +" "+ information);
    }
}
