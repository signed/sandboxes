package tck;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.AnnotationSupport;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TckTest {

    @Test
    void testsAreTagged() {
        var testClasses = testMethods("examples");

        testClasses.forEach(classWithTests -> assertIsTagged(classWithTests));
    }

    private void assertIsTagged(Class<?> classWithTests) {
        var tagAnnotations = AnnotationSupport.findRepeatableAnnotations(classWithTests, Tag.class);
        assertThat(tagAnnotations).withFailMessage("@Tag required in " + classWithTests.getSimpleName()).isNotEmpty();
    }

    private @NonNull Set<Class<?>> testMethods(String projectPackage) {
        Reflections reflections = new Reflections(projectPackage, Scanners.MethodsAnnotated);
        var methods = reflections.getMethodsAnnotatedWith(Test.class);
        return methods.stream().map(Method::getDeclaringClass).collect(Collectors.toSet());
    }
}
