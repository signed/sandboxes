package com.github.signed.sandboxes.jee.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.signed.sandboxes.jee.webservice.HelloWorld")
public class HelloWorldImpl implements HelloWorld{
 
	@Override
    public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}
 
}