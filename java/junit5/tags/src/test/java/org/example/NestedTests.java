package org.example;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestTag;
import org.junit.platform.testkit.engine.EngineTestKit;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class NestedTests {

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
                .allMatch(it-> it.getTags().contains(TestTag.create("tag-on-the-container")));
    }
}
