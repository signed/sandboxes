package optionsobject;

import optionsobject.OptionalOptions.OperationOptions;
import org.junit.jupiter.api.Test;

import static optionsobject.OptionalOptions.someOperation;

class OptionalOptionsTest {

    @Test
    void name() {
        someOperation();
        someOperation(arg -> arg.verbose().dryRun());
        someOperation(OperationOptions.defaultOptions().verbose().dryRun());
    }
}