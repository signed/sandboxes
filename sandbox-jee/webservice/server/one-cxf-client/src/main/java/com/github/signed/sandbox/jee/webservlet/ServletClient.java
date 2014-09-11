package com.github.signed.sandbox.jee.webservlet;

import java.io.IOException;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

public class ServletClient {

    public static void main(String[] args) throws IOException {
        Content content = Request.Get("http://localhost:8080/one-war/async").execute().returnContent();
        System.out.println(content);
//        Request.Post("http://targethost/login").bodyForm(Form.form().add("username", "vip").add("password", "secret").build())
//                .execute().returnContent();
    }
}
