package manual;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.Events;

import static manual.JupiterTestEngineRemote.executeTestsIn;

public class ManualScriptTest{

    @Test
    void doNotRun() {
        Events tests = executeTestsIn(DoExecuteScripts.class).testEvents();

        tests.assertStatistics(stats -> stats.started(0).succeeded(0).skipped(2));
    }

    @Test
    void doRun() {
        Events tests = executeTestsIn(ExecuteScripts.class).testEvents();

        tests.assertStatistics(stats -> stats.started(1).succeeded(1).skipped(0));
    }

    static class DoExecuteScripts {


        @ManualScript
        void inactiveByDefault() {
            Assertions.fail("Default inactive manual script is not executed");
        }

        @ManualScript(ScriptStatus.Inactive)
        void explicitlySetAsInactive() {
            Assertions.fail("Explicitly inactive manual script is not executed");
        }
    }

    static class ExecuteScripts {
        @ManualScript(ScriptStatus.Primed)
        void executePrimedManualScripts() {
        }
    }
}
