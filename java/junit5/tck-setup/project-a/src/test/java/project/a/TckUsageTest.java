package project.a;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import tck.TckBuilder;

import java.util.stream.Stream;

public class TckUsageTest {

    @TestFactory
    Stream<? extends DynamicNode> executeTck() {
       return TckBuilder
                .forProjectPackage("project.a")
                .testClassesAreTagged()
                .build();
    }
}
