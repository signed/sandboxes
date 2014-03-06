package com.github.signed.sandboxes.webservice.hello;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.signed.sandboxes.webservice.hello.HelloWorld")
public class HelloWorldImpl implements HelloWorld{
 
	@Override
    public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}
 
}