package tck;

import org.junit.jupiter.api.DynamicNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class TckBuilder {

    public static TckBuilder forProjectPackage(String packageName) {
        return new TckBuilder().projectPackage(packageName);
    }

    private String packageName;
    private List<Function<ClasspathScanner, Check>> checks = new ArrayList<>();


    private TckBuilder projectPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public TckBuilder testClassesAreTagged() {
        checks.add(TestClassesAreTagged::new);
        return this;
    }

    public Stream<? extends DynamicNode> build() {
        var scanner = new ClasspathScanner(packageName);
        return checks.stream()
                .map(it -> it.apply(scanner))
                .map(it -> dynamicContainer(it.name(), it.stream()));
    }
}
