package com.github.signed.jupiter.retry

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class RetryTestExtensionTest {

    @BeforeEach
    internal fun setUp(retryInformation: RetryInformation) {
        println("setup: " + retryInformation.invocation)
    }

    @AfterEach
    internal fun tearDown(retryInformation: RetryInformation) {
        println("tearDown: " + retryInformation.invocation)
    }

    @Retry(times = 2)
    fun `jump and run`(retryInformation: RetryInformation) {
        println("invocation: " + retryInformation.invocation)
        when (retryInformation.invocation) {
            3 -> println("success")
            else -> {
                println("fail")
                throw RuntimeException("unlucky")
            }
        }
    }

}