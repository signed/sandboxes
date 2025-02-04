package manual;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.Events;

public class ManualScriptTest extends AbstractJupiterTestEngineTests {

    @Test
    void doNotRun() {
        Events tests = executeTestsForClass(DoNotRunTests.class).testEvents();

        tests.assertStatistics(stats -> stats.started(0).succeeded(0).skipped(2));
    }

    static class DoNotRunTests {

        @ManualScript
        void inactiveByDefault() {
            Assertions.fail("Default inactive manual script is not executed");
        }

        @ManualScript(ScriptStatus.Inactive)
        void explicitlySetAsInactive() {
            Assertions.fail("Explicitly inactive manual script is not executed");
        }
    }

    @Test
    void doRun() {
        Events tests = executeTestsForClass(ExecuteTests.class).testEvents();

        tests.assertStatistics(stats -> stats.started(1).succeeded(1).skipped(0));
    }


    static class ExecuteTests {
        @ManualScript(ScriptStatus.Primed)
        void executePrimedManualScripts() {
        }
    }
}
