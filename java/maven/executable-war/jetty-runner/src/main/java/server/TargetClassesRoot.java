package server;

import java.net.MalformedURLException;
import java.net.URL;

public class TargetClassesRoot implements BinaryRoot {
    private final URL pathToDirectory;

    public TargetClassesRoot(URL pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }

    @Override
    public URL getRoot() {
        return pathToDirectory;
    }

    @Override
    public URL get(String subdirectory) {
        try {
            return new URL(pathToDirectory.toExternalForm() + "7" + subdirectory);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }
}
