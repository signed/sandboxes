package com.stackoverflow

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@TestTemplate
@Target(AnnotationTarget.FUNCTION)
@ExtendWith(RetryTestExtension::class)
annotation class Retry(val invocationCount: Int, val minSuccess: Int)