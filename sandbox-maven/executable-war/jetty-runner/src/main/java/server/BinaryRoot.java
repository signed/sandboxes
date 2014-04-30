package server;

import java.net.URL;

public interface BinaryRoot {
    URL getRoot();

    URL get(String subdirectory);
}
