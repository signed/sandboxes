package tck;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Tag;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class TestClassesAreTagged implements Check {

    private final ClasspathScanner scanner;

    public TestClassesAreTagged(ClasspathScanner scanner) {
        this.scanner = scanner;
    }

    public void assertIsTagged(Class<?> classWithTests) {
        var tagAnnotations = AnnotationSupport.findRepeatableAnnotations(classWithTests, Tag.class);
        assertThat(tagAnnotations).withFailMessage("@Tag required in " + classWithTests.getSimpleName()).isNotEmpty();
    }

    @Override
    public String name() {
        return "Classes Containing Test are Tagged";
    }

    @Override
    public Stream<DynamicNode> stream() {
        return scanner.classesWithTestMethods().stream().map(testClass -> dynamicTest(testClass.getSimpleName(), () -> assertIsTagged(testClass)));
    }
}
