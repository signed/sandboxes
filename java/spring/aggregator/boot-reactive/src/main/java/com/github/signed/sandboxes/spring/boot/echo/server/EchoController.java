package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.signed.sandboxes.spring.boot.echo.api.EchoTransferObject;

@RestController
public class EchoController {

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public EchoTransferObject echoTransferObject(@RequestBody EchoTransferObject transferObject) {
        System.out.println(transferObject.message);
        System.out.println(transferObject.responseDelayInMilliseconds);
        try {
            Thread.sleep(transferObject.responseDelayInMilliseconds);
            EchoTransferObject response = new EchoTransferObject();
            response.responseDelayInMilliseconds = transferObject.responseDelayInMilliseconds;
            response.message = transferObject.message +" "+ transferObject.message;
            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
