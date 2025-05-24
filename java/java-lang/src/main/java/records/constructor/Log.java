package records.constructor;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private final List<String> lines = new ArrayList<>();

    void add(String line) {
        lines.add(line);
    }

    List<String> lines(){
        return List.copyOf(lines);
    }
}
