package timetillfirsttest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LogExtensionExecution.class)
public class LogExtensionExecutionTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Test Before All");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Test Before Each");
    }

    @Test
    void one() {
        System.out.println("Test");
    }

    @Test
    void two() {
        System.out.println("Test");
    }
}
