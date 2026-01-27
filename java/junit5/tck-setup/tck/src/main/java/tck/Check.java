package tck;

import org.junit.jupiter.api.DynamicNode;

import java.util.stream.Stream;

public interface Check {
    String name();

    Stream<DynamicNode> stream();
}
