package com.example.project

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(DiagnosticExtension::class)
class CalculatorTests {

    @Test
    fun `1 + 1 = 2`() {
        val calculator = Calculator()
        assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2")
    }

    @BeforeEach
    fun setUp() {
        println("CalculatorTests.setUp")
    }

    @AfterEach
    fun tearDown() {
        println("CalculatorTests.tearDown")
    }

    @Test
    fun `raise exception`() {
        throw RuntimeException("see what is called")
    }
}
