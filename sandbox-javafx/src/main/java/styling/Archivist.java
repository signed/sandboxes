package styling;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.Files.exists;

public class Archivist {

    public String retrieveStyleFrom(Path path) {
        if (exists(path)) {
            return loadStyle(path);
        }
        return "no file found at "+path.toString();
    }

    public void transcribeTo(Path path,String style) {
        String[] lines = style.split("\n");
        ArrayList<String> strings = new ArrayList<>();


        Collections.addAll(strings, lines);
        try {
            Files.write(path, strings, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadStyle(Path path) {
        List<String> strings = readFile(path);
        StringBuilder all = new StringBuilder();
        for (String string : strings) {
            all.append(string).append("\n");
        }
        return all.toString();
    }

    private List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
