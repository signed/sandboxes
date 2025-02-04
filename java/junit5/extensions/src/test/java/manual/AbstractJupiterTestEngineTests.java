package manual;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.reporting.OutputDirectoryProvider;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public abstract class AbstractJupiterTestEngineTests {

	public static OutputDirectoryProvider dummyOutputDirectoryProvider() {
		return new OutputDirectoryProvider() {
			@Override
			public Path getRootDirectory() {
				throw new NotImplementedException();
			}

			@Override
			public Path createOutputDirectory(TestDescriptor testDescriptor) {
				throw new NotImplementedException();
			}
		};
	}

	private final JupiterTestEngine engine = new JupiterTestEngine();

	protected EngineExecutionResults executeTestsForClass(Class<?> testClass) {
		return executeTests(selectClass(testClass));
	}

	protected EngineExecutionResults executeTests(DiscoverySelector... selectors) {
		return executeTests(request().selectors(selectors).outputDirectoryProvider(dummyOutputDirectoryProvider()));
	}

	protected EngineExecutionResults executeTests(LauncherDiscoveryRequestBuilder builder) {
		return executeTests(builder.build());
	}

	protected EngineExecutionResults executeTests(LauncherDiscoveryRequest request) {
		return EngineTestKit.execute(this.engine, request);
	}

}
