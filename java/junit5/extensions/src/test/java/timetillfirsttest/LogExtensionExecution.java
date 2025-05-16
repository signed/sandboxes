package timetillfirsttest;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LogExtensionExecution implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("Extension Before All");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("Extension Before Each");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.println("Extension Before TestExecution");
    }
}
