package com.github.signed.sandboxes.jee.webservlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.signed.sandboxes.jee.async.AsyncBean;

@WebServlet(name = "AsyncTestServlet", urlPatterns = {"/async"})
public class AsyncTestServlet extends HttpServlet {

    @Inject
    AsyncBean asyncBean;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("caller method running in thread " + Thread.currentThread().getName());
        asyncBean.asyncMethod();
        response.getWriter().append("Hello you");
        System.out.println("caller method returned in thread " + Thread.currentThread().getName());
    }
}