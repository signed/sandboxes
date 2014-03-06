package com.github.signed.sandboxes.jee.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use= SOAPBinding.Use.LITERAL)
public interface HelloWorld{
 
	@WebMethod String getHelloWorldAsString(String name);
 
}