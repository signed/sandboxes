package org;

import org.junit.jupiter.api.Test;

import static org.Assertions.assertThat;

class DomainObjectTest {

    @Test
    void basicExample() {
        var d = new DomainObject();
        d.age = 22;
        d.name = "John Doe";

        assertThat(d).hasAge(22).hasName("John Doe");
    }
}