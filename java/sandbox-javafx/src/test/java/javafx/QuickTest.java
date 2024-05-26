package javafx;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class QuickTest {

    @Test(expected = ConnectException.class)
    public void testName() throws Exception {
        URL url = new URL("http://localhost:8018");
        URLConnection connection = url.openConnection();
        connection.connect();

    }

    @Test(expected = FileNotFoundException.class)
    public void file() throws Exception {
        URL url = new URL("file:///tmp/");
        URLConnection connection = url.openConnection();
        connection.connect();

    }
}
