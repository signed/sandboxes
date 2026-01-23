package org.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestTag;
import org.junit.platform.testkit.engine.EngineTestKit;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class NestedTests {

    @Tag("tag-on-the-container")
    public static class ContainerWithNestedTests {

        @Nested
        class One {
            @Test
            void one_method() {
            }
        }

        @Nested
        class Two {
            @Test
            void two_method() {
            }
        }
    }

    @Test
    void tagsFromTheContainerPropagateToNestedTests() {
        var results = EngineTestKit.engine("junit-jupiter")
                .selectors(selectClass(ContainerWithNestedTests.class))
                .discover();

        assertEquals(emptyList(), results.getDiscoveryIssues());
        var descriptor = results.getEngineDescriptor();
        assertEquals("JUnit Jupiter", descriptor.getDisplayName());

        var tests = CollectingVisitor.collectMatching(TestDescriptor::isTest);
        descriptor.accept(tests);
        assertThat(tests.matches)
                .hasSize(2)
                .allMatch(it -> it.getTags().contains(TestTag.create("tag-on-the-container")));
    }
}
