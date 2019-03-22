package server;

import java.net.MalformedURLException;
import java.net.URL;

public class JarRoot implements BinaryRoot {
    private final URL jarLocation;

    public JarRoot(URL jarLocation){
        this.jarLocation = jarLocation;
    }

    @Override
    public URL getRoot() {
        return linkIntoJar("");
    }

    @Override
    public URL get(String subdirectory) {
        return linkIntoJar(subdirectory);
    }

    private URL linkIntoJar(String sub) {
        String pathIntoJar = "jar:" + jarLocation.toExternalForm() + "!/" + sub;
        try {
            return new URL(pathIntoJar);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
