package tck;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class ClasspathScanner {
    private final String packageName;

    public ClasspathScanner(String packageName) {
        this.packageName = packageName;
    }

    public @NonNull Set<Class<?>> classesWithTestMethods() {
        Reflections reflections = new Reflections(packageName, Scanners.MethodsAnnotated);
        var methods = reflections.getMethodsAnnotatedWith(Test.class);
        return methods.stream().map(Method::getDeclaringClass).collect(Collectors.toSet());
    }
}
