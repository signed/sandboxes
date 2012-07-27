package styling;

import java.util.List;

public interface StyleClassSink {
    void consume(List<String> styleClasses);
}
