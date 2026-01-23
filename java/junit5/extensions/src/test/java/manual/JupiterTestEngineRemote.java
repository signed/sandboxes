package manual;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.OutputDirectoryCreator;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class JupiterTestEngineRemote {

    public static EngineExecutionResults executeTestsIn(Class<?> testClass) {
        final LauncherDiscoveryRequestBuilder builder = request()
                .selectors(selectClass(testClass))
                .outputDirectoryCreator(new StandInOutputDirectoryCreator());
        return EngineTestKit.execute(new JupiterTestEngine(), builder.build());
    }

    private static class StandInOutputDirectoryCreator implements OutputDirectoryCreator {

        @Override
        public Path getRootDirectory() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Path createOutputDirectory(TestDescriptor testDescriptor) throws IOException {
            throw new RuntimeException("Not implemented");
        }
    }
}
