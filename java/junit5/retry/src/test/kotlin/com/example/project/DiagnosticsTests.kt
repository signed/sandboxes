package com.example.project

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DiagnosticExtension::class)
class DiagnosticsTests {

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            println("Test.beforeAll")
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            println("Test.afterAll")
        }
    }

    @BeforeEach
    fun setUp() {
        println("Test.setUp")
    }

    @AfterEach
    fun tearDown() {
        println("Test.tearDown")
    }

    @Test
    fun `1 + 1 = 2`() {
        val calculator = Calculator()
        assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2")
    }

    @Test
    fun `raise exception`() {
        throw RuntimeException("see what is called")
    }
}
