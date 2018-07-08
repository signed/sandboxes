package com.example.project

import org.junit.jupiter.api.extension.*

// https://stackoverflow.com/questions/46181026/junit5-how-to-repeat-failed-test
class DiagnosticExtension :
        BeforeAllCallback,
        TestInstancePostProcessor,
        ExecutionCondition,
        BeforeEachCallback,
        BeforeTestExecutionCallback,
        TestExecutionExceptionHandler,
        AfterTestExecutionCallback,
        AfterEachCallback,
        AfterAllCallback
{
    override fun beforeAll(context: ExtensionContext?) {
        println("Extension.beforeAll")
    }

    override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
        println("Extension.postProcessTestInstance")
    }

    override fun evaluateExecutionCondition(context: ExtensionContext?): ConditionEvaluationResult {
        println("Extension.evaluateExecutionCondition")
        return ConditionEvaluationResult.enabled("because we can")
    }

    override fun beforeEach(context: ExtensionContext?) {
        println("Extension.beforeEach")
    }

    override fun beforeTestExecution(context: ExtensionContext?) {
        println("Extension.beforeTestExecution")
    }

    override fun handleTestExecutionException(context: ExtensionContext?, throwable: Throwable?) {
        println("Extension.handleTestExecutionException")
    }

    override fun afterTestExecution(context: ExtensionContext?) {
        println("Extension.afterTestExecution")
    }

    override fun afterEach(context: ExtensionContext?) {
        println("Extension.afterEach")
    }

    override fun afterAll(context: ExtensionContext?) {
        println("Extension.afterAll")
    }
}
