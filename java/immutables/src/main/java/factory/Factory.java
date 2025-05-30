package factory;

import org.immutables.builder.Builder;
import org.immutables.value.Value;

@Value.Style( build = "invoke", newBuilder = "create", typeInnerBuilder = "Hello")
public class Factory {

    @Builder.Factory
    public static int sum(int a, int b) {
        return a + b;
    }
}
