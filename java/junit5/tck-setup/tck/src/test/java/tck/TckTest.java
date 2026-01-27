package tck;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class TckTest {

    @TestFactory
    Stream<? extends DynamicNode> usingTheBuilder(){
        return TckBuilder
                .forProjectPackage("examples")
                .testClassesAreTagged()
                .build();
    }
}
