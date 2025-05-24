package stacktrace;

import org.junit.jupiter.api.Test;

class ObtainAndPrint_Test {

    @Test
    void runSample() throws Exception {
        new ObtainAndPrint(System.out).println("output");
    }
}
