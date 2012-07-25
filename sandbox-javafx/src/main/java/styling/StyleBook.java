package styling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleBook {
    private final Map<String, StyleClass> styleByClassName = new HashMap<>();

    public void addStyleClasses(List<String> strings) {
        for (String styleClass : strings) {
          styleByClassName.put(styleClass, new StyleClass(styleClass));
        }
    }
}
