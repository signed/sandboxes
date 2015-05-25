package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import com.github.signed.sandboxes.spring.boot.echo.EchoTransferObject;

import rx.Observable;


@RestController
@EnableAutoConfiguration
public class SampleController {

    public static class DelayEchoServiceClient {
        public Observable<ResponseEntity<EchoTransferObject>> callWith(String message, long delayInMilliseconds) {
            AsyncRestTemplate rest = new AsyncRestTemplate();

            ListenableFuture<ResponseEntity<EchoTransferObject>> first = rest.postForEntity("http://localhost:9000/", echoTransferObject(message, delayInMilliseconds), EchoTransferObject.class);
            return Observable.from(first);
        }

        private HttpEntity<EchoTransferObject> echoTransferObject(String message, long delay) {
            EchoTransferObject requestObject = new EchoTransferObject();
            requestObject.message = message;
            requestObject.responseDelayInMilliseconds = delay;
            return new HttpEntity<>(requestObject);
        }

    }

    private final DelayEchoServiceClient client = new DelayEchoServiceClient();

    @RequestMapping("/")
    @ResponseBody
    public ListenableFuture<TransferObject> home() {
        SettableListenableFuture<TransferObject> result = new SettableListenableFuture<>();
        Observable<ResponseEntity<EchoTransferObject>> firstObservable = client.callWith("first", 2000);
        Observable<ResponseEntity<EchoTransferObject>> secondObservable = client.callWith("second", 4000);

        Observable<TransferObject> zipped = Observable.zip(firstObservable, secondObservable, (first1, second1) -> {
            EchoTransferObject firstResponse = first1.getBody();
            EchoTransferObject secondResponse = second1.getBody();
            TransferObject result1 = new TransferObject();
            result1.content = firstResponse.message + "|" + secondResponse.message;
            return result1;
        });
        zipped.subscribe(result::set, result::setException);
        return result;
    }

}
