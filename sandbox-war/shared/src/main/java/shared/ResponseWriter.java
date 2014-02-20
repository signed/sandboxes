package shared;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Properties;


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
        writer.println("<h1>"+identifier+" Servlet "+ new Date()+"</h1>");
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
        if( resourceUrl != null){
            Properties properties = new Properties();
            builder.append("<br>");
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream(resource));
                builder.append(properties.getProperty("configuration"));
            } catch (IOException e) {
                builder.append("could not read");
            }

        }
        builder.append("<br>");
        return builder;
    }
}
