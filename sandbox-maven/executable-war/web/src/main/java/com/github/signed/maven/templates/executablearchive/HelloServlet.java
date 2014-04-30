package com.github.signed.maven.templates.executablearchive;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    public static final String InitParameter_Identifier = "identifier";
    private Optional<String> identifier;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        identifier = Optional.fromNullable(config.getInitParameter(InitParameter_Identifier));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTime now = new DateTime();
        PrintWriter out = resp.getWriter();
        out.println("[" + identifier.or("default") + "][" + now + "]hello, world! This time for real");
        out.close();
    }
}
