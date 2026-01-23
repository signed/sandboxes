package org.example;

import org.junit.platform.engine.TestDescriptor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

class CollectingVisitor implements TestDescriptor.Visitor {

    public static CollectingVisitor collectMatching(Predicate<? super TestDescriptor> predicate) {
        return new CollectingVisitor(predicate);
    }

    private final Predicate<? super TestDescriptor> predicate;

    public CollectingVisitor(Predicate<? super TestDescriptor> predicate) {
        this.predicate = predicate;
    }

    Set<TestDescriptor> matches = new HashSet<>();

    @Override
    public void visit(TestDescriptor descriptor) {
        if (predicate.test(descriptor)) {
            matches.add(descriptor);
        }
    }
}
