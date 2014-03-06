package com.github.signed.sandboxes.jee.endpoint;

import com.github.signed.sandboxes.jee.webservice.HelloWorldImpl;

import javax.xml.ws.Endpoint;
 
public class HelloWorldPublisher{
 
	public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
    }
 
}