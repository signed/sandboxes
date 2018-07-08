package com.github.signed.jupiter.retry

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate

class RetryTestExtensionTest {

    @BeforeEach
    internal fun setUp(retryInformation: RetryInformation) {
        println("setup " + retryInformation.invocation)
    }

    @TestTemplate
    @Retry(count = 2)
    fun `jump and run`(retryInformation: RetryInformation) {
        when (retryInformation.invocation) {
            1, 2 -> {
                println("invocation: " + retryInformation.invocation)
                throw RuntimeException("something went wrong")
            }

            3 -> print("second")
        }

        println("jup " + retryInformation.invocation)
    }

}