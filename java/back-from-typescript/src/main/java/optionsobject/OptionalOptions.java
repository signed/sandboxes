package optionsobject;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalOptions {

    public record OperationOptions(boolean verbose, boolean dryRun) {

        // why can't this be named verbose?
        public static OperationOptionsBuilder verbose2(){
            return defaultOptions().verbose();
        }

        public static void verbose(OperationOptionsBuilder builder){
            builder.verbose();
        }

        public static OperationOptionsBuilder defaultOptions() {
            return new OperationOptionsBuilder().verbose(false).dryRun(false);
        }

        public static void dryRun(OperationOptionsBuilder builder){
            builder.dryRun();
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

    public static OperationOptions someOperation() {
        return someOperation(OperationOptions.defaultOptions());
    }

    public static OperationOptions someOperation(Consumer<OperationOptions.OperationOptionsBuilder> optionsConsumer) {
        final var builder = OperationOptions.defaultOptions();
        optionsConsumer.accept(builder);
        return someOperation(builder);
    }

    public static OperationOptions someOperation(OperationOptions.OperationOptionsBuilder options) {
        return options.build();
    }
}

