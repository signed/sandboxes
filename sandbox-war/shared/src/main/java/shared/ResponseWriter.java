package shared;

import java.io.PrintWriter;
import java.net.URL;


public class ResponseWriter {

    private final String identifier;

    public ResponseWriter(String identifier) {
        this.identifier = identifier;
    }

    public void writeInto(PrintWriter writer) {
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>"+identifier+"</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");

        writer.println("<table border=\"0\" cellpadding=\"10\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<h1>"+identifier+" Servlet</h1>");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");


        writer.write(resourceAsString("configuration.properties").toString());
        writer.write(resourceAsString("props/hornetq-roles.properties").toString());

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        writer.append(new HtmlClassPathPrinter().classpathAsHtmlString(systemClassLoader, "SystemClassloader"));

        ClassLoader classLoader = this.getClass().getClassLoader();


        int level = 0;
        while (classLoader != null) {
            writer.append(new HtmlClassPathPrinter().classpathAsHtmlString(classLoader, "Level " + level++));
            classLoader = classLoader.getParent();
        }

        writer.println("</body>");
        writer.println("</html>");
    }

    private StringBuilder resourceAsString(String resource) {
        URL resourceUrl = this.getClass().getClassLoader().getResource(resource);
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("The url to "+resource +"  is: " + resourceUrl);
        builder.append("<br>");
        return builder;
    }
}
