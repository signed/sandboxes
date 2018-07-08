package com.stackoverflow;

import org.junit.jupiter.api.extension.*
import org.opentest4j.TestAbortedException

internal class RetryingTestExecutionExtension(
        private val invocation: Int,
        private val maxInvocations: Int,
        private val minSuccess: Int) : ExecutionCondition, ParameterResolver, TestExecutionExceptionHandler {
    override fun evaluateExecutionCondition(context: ExtensionContext): ConditionEvaluationResult {
        val failureCount = getFailures(context).size
        // Shift -1 because this happens before test
        val successCount = (invocation - 1) - failureCount
        return when {
            (maxInvocations - failureCount) < minSuccess -> // Case when we cannot hit our minimum success
                ConditionEvaluationResult.disabled("Cannot hit minimum success rate of $minSuccess/$maxInvocations - $failureCount failures already")
            successCount < minSuccess -> // Case when we haven't hit success threshold yet
                ConditionEvaluationResult.enabled("Have not ran $minSuccess/$maxInvocations successful executions")
            else -> ConditionEvaluationResult.disabled("$minSuccess/$maxInvocations successful runs have already ran. Skipping run $invocation")
        }
    }

    override fun supportsParameter(
            parameterContext: ParameterContext,
            extensionContext: ExtensionContext
    ): Boolean = parameterContext.parameter.type == RetryInfo::class.java

    override fun resolveParameter(
            parameterContext: ParameterContext,
            extensionContext: ExtensionContext
    ): Any = RetryInfo(invocation, maxInvocations)

    override fun handleTestExecutionException(
            context: ExtensionContext,
            throwable: Throwable
    ) {

        val testFailure = RetryingTestFailure(invocation, throwable)
        val failures: MutableList<RetryingTestFailure> = getFailures(context)
        failures.add(testFailure)
        val failureCount = failures.size
        val successCount = invocation - failureCount
        if ((maxInvocations - failureCount) < minSuccess) {
            throw testFailure
        } else if (successCount < minSuccess) {
            // Case when we have still have retries left
            throw TestAbortedException("Aborting test #$invocation/$maxInvocations- still have retries left",
                    testFailure)
        }
    }

    private fun getFailures(context: ExtensionContext): MutableList<RetryingTestFailure> {
        val namespace = ExtensionContext.Namespace.create(RetryingTestExecutionExtension::class.java)
        val store = context.parent.get().getStore(namespace)
        @Suppress("UNCHECKED_CAST")
        return store.getOrComputeIfAbsent(context.requiredTestMethod.name, { mutableListOf<RetryingTestFailure>() }, MutableList::class.java) as MutableList<RetryingTestFailure>
    }
}