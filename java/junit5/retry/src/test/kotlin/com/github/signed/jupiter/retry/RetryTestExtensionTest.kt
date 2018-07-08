package com.github.signed.jupiter.retry

import org.junit.jupiter.api.TestTemplate

class RetryTestExtensionTest {

    @TestTemplate
    @Retry
    fun `jump and run`(retryInformation: RetryInformation) {
        println("jup")
    }

}