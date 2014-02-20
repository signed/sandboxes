package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Simple Hello servlet.
 */

public final class Hello extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        URL resource = Hello.class.getClassLoader().getResource("configuration.properties");
        URL xml = this.getClass().getClassLoader().getResource("props/hornetq-roles.properties");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Sample Application Servlet Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");

        writer.println("<table border=\"0\" cellpadding=\"10\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<h1>Sample Application Servlet</h1>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");

        writer.println("This is the output of a servlet that is part of");
        writer.println("the Hello, World application.");

        writer.print("<br>");
        writer.println("the resource is " + resource);
        writer.print("<br>");

        writer.print("<br>");
        writer.println("the xml-resource is " + xml);
        writer.print("<br>");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        writer.append(classpathAsHtmlString(systemClassLoader, "SystemClassloader"));

        ClassLoader classLoader = Hello.class.getClassLoader();


        int level = 0;
        while (classLoader != null) {
            writer.append(classpathAsHtmlString(classLoader, "Level " + level++));
            classLoader = classLoader.getParent();
        }

        writer.println("</body>");
        writer.println("</html>");
    }

    private String classpathAsHtmlString(ClassLoader classLoader, String name) {
        if (classLoader instanceof URLClassLoader) {
            URL[] urLs = ((URLClassLoader) classLoader).getURLs();
            StringBuilder builder = new StringBuilder();
            builder.append(name);
            builder.append("<br>");
            builder.append("<ol>");
            for (URL urL : urLs) {
                builder.append("<li>");
                builder.append(urL);
                builder.append("</li>");
            }
            builder.append("</ol>");

            return builder.toString();
        }
        return classLoader.getClass().toString();
    }
} 