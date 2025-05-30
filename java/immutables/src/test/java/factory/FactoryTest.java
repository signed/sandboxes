package factory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryTest {

    @Test
    void methodInvocationBuilder() {
        var build = SumBuilder.create().a(5).b(7).invoke();
        assertThat(build).isEqualTo(12);
    }
}