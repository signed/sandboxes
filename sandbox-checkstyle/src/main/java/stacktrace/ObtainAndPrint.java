package stacktrace;

import java.io.PrintStream;
import java.text.MessageFormat;

public class ObtainAndPrint extends PrintStream {

    public ObtainAndPrint(PrintStream out) {
        super(out);
    }

    @Override
    public void println(Object x) {
        prependLineInformation();
        super.println(x);
    }

    @Override
    public void println(String x) {
        prependLineInformation();
        super.println(x);
    }

    private void prependLineInformation() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        super.print(MessageFormat.format("({0}:{1, number,#}): ", element.getFileName(), element.getLineNumber()));
    }

}
