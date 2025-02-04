package manual;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.reporting.OutputDirectoryProvider;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class JupiterTestEngineRemote {


	public static  EngineExecutionResults executeTestsIn(Class<?> testClass) {
		final LauncherDiscoveryRequestBuilder builder = request()
				.selectors(selectClass(testClass))
				.outputDirectoryProvider(new StandInOutputDirectoryProvider());
        return EngineTestKit.execute(new JupiterTestEngine(), builder.build());
	}

	private static class StandInOutputDirectoryProvider implements OutputDirectoryProvider {
		@Override
		public Path getRootDirectory() {
			throw new NotImplementedException();
		}

		@Override
		public Path createOutputDirectory(TestDescriptor testDescriptor) {
			throw new NotImplementedException();
		}
	}
}
