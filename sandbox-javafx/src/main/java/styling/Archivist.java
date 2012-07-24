package styling;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.Files.exists;

public class Archivist {
    private final Path styleFile = Paths.get("style.css");

    public String retrieveStyle() {
        if (exists(styleFile)) {
            return loadStyle();
        } else {
            createStyleFile();
            return "";
        }
    }

    private void createStyleFile(){
        try {
            Files.createFile(styleFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String loadStyle() {
        List<String> strings = readFile();
        StringBuilder all = new StringBuilder();
        for (String string : strings) {
            all.append(string).append("\n");
        }
        return all.toString();
    }

    private List<String> readFile() {
        try {
            return Files.readAllLines(styleFile, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void transcribe(String style) {
        String[] lines = style.split("\n");
        ArrayList<String> strings = new ArrayList<>();

        Collections.addAll(strings, lines);
        try {
            Files.write(styleFile, strings, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
