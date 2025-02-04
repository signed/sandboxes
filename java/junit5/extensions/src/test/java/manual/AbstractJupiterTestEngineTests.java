package manual;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.reporting.OutputDirectoryProvider;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class AbstractJupiterTestEngineTests {

	public static OutputDirectoryProvider dummyOutputDirectoryProvider() {
		return new OutputDirectoryProvider() {
			@Override
			public Path getRootDirectory() {
				throw new NotImplementedException();
			}

			@Override
			public Path createOutputDirectory(TestDescriptor testDescriptor) throws IOException {
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

	protected TestDescriptor discoverTests(DiscoverySelector... selectors) {
		return discoverTests(
				request().selectors(selectors).outputDirectoryProvider(dummyOutputDirectoryProvider()).build());
	}

	protected TestDescriptor discoverTests(LauncherDiscoveryRequest request) {
		return engine.discover(request, UniqueId.forEngine(engine.getId()));
	}

	protected UniqueId discoverUniqueId(Class<?> clazz, String methodName) {
		TestDescriptor engineDescriptor = discoverTests(selectMethod(clazz, methodName));
		Set<? extends TestDescriptor> descendants = engineDescriptor.getDescendants();
		// @formatter:off
		TestDescriptor testDescriptor = descendants.stream()
				.skip(descendants.size() - 1)
				.findFirst()
				.orElseGet(() -> fail("no descendants"));
		// @formatter:on
		return testDescriptor.getUniqueId();
	}

}
