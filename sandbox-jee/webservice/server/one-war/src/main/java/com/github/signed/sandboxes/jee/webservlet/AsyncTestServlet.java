package com.github.signed.sandboxes.jee.webservlet;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        Future<String> future = asyncBean.asyncMethod();
        try {
            response.getWriter().append(future.get());
        } catch (InterruptedException | ExecutionException e) {
            response.getWriter().append("fail");
        }
        System.out.println("caller method returned in thread " + Thread.currentThread().getName());
    }
}