package shared;

import java.net.URL;
import java.net.URLClassLoader;

public class HtmlClassPathPrinter {

    public String classpathAsHtmlString(ClassLoader classLoader, String name) {
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
