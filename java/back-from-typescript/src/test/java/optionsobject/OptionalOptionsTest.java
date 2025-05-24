package optionsobject;

import optionsobject.OptionalOptions.OperationOptions;
import org.junit.jupiter.api.Test;

import static optionsobject.OptionalOptions.OperationOptions.defaultOptions;
import static optionsobject.OptionalOptions.someOperation;
import static org.assertj.core.api.Assertions.assertThat;

class OptionalOptionsTest {

    @Test
    void useDefaultOptionsByDefault() {
        var options = someOperation();

        assertThat(options.dryRun()).isFalse();
        assertThat(options.verbose()).isFalse();
    }

    /**
     * <ul>
     *     <li>default opts are provided into the lambda, no need to import/create it when calling</li>
     * </ul>
     */
    @Test
    void passingWithConsumer() {
        {
            var options = someOperation(opts -> opts.verbose().dryRun());
            assertThat(options.dryRun()).isTrue();
            assertThat(options.verbose()).isTrue();
        }

        {
            var options = someOperation(OperationOptions::verbose);
            assertThat(options.dryRun()).isFalse();
            assertThat(options.verbose()).isTrue();
        }

        {
            var options = someOperation(OperationOptions::dryRun);
            assertThat(options.dryRun()).isTrue();
            assertThat(options.verbose()).isFalse();
        }
    }

    @Test
    void passingWithBuilder() {
        {
            var options = someOperation(defaultOptions().verbose().dryRun());
            assertThat(options.dryRun()).isTrue();
            assertThat(options.verbose()).isTrue();
        }
        {
            var options = someOperation(OperationOptions.verbose2());
            assertThat(options.dryRun()).isFalse();
            assertThat(options.verbose()).isTrue();
        }
    }
}