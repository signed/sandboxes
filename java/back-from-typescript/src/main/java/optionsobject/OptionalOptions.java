package optionsobject;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalOptions {

    public record OperationOptions(boolean verbose, boolean dryRun) {
        public static OperationOptionsBuilder defaultOptions() {
            return new OperationOptionsBuilder().verbose(false).dryRun(false);
        }

        public static class OperationOptionsBuilder {
            private Optional<Boolean> verbose = Optional.empty();
            private Optional<Boolean> dryRun = Optional.empty();

            public OperationOptionsBuilder verbose() {
                return verbose(Boolean.TRUE);
            }

            /* intentional private only used to set the default value */
            private OperationOptionsBuilder verbose(Boolean value) {
                verbose = Optional.of(value);
                return this;
            }

            public OperationOptionsBuilder dryRun() {
                return dryRun(Boolean.TRUE);
            }

            /* intentional private only used to set the default value */
            private OperationOptionsBuilder dryRun(Boolean value) {
                dryRun = Optional.of(value);
                return this;
            }

            public OperationOptions build() {
                final var verbose = this.verbose.orElseThrow();
                final var dryRun = this.dryRun.orElseThrow();
                return new OperationOptions(verbose, dryRun);
            }

        }
    }

    public static void someOperation() {
        someOperation(OperationOptions.defaultOptions());

    }

    public static void someOperation(Consumer<OperationOptions.OperationOptionsBuilder> optionsConsumer) {
        final var builder = OperationOptions.defaultOptions();
        optionsConsumer.accept(builder);
    }

    public static void someOperation(OperationOptions.OperationOptionsBuilder options) {
        final var flup = options.build();

    }
}
