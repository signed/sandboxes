package com.example.project

import org.junit.jupiter.api.extension.*
import org.opentest4j.TestAbortedException

// https://stackoverflow.com/questions/46181026/junit5-how-to-repeat-failed-test
class DiagnosticExtension : AfterTestExecutionCallback,
        ExecutionCondition,
        BeforeTestExecutionCallback,
        BeforeEachCallback,
        AfterEachCallback,
        AfterAllCallback,
        BeforeAllCallback,
        TestExecutionExceptionHandler,
        TestInstancePostProcessor
{
    override fun beforeAll(context: ExtensionContext?) {
        println("RetryExtension.beforeAll")
    }

    override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
        println("RetryExtension.postProcessTestInstance")
    }

    override fun beforeEach(context: ExtensionContext?) {
        println("RetryExtension.beforeEach")
    }

    override fun beforeTestExecution(context: ExtensionContext?) {
        println("RetryExtension.beforeTestExecution")
    }

    override fun handleTestExecutionException(context: ExtensionContext?, throwable: Throwable?) {
        println("RetryExtension.handleTestExecutionException")
        throw TestAbortedException("this is ugly")
    }

    override fun afterTestExecution(context: ExtensionContext?) {
        println("RetryExtension.afterTestExecution")
    }

    override fun afterEach(context: ExtensionContext?) {
        println("RetryExtension.afterEach")
    }

    override fun afterAll(context: ExtensionContext?) {
        println("RetryExtension.afterAll")
    }

    override fun evaluateExecutionCondition(context: ExtensionContext?): ConditionEvaluationResult {
        return ConditionEvaluationResult.enabled("because we can")
    }
}