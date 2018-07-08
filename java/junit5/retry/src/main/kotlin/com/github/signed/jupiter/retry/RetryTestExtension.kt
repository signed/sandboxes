package com.github.signed.jupiter.retry

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport
import org.opentest4j.TestAbortedException
import java.util.stream.IntStream
import java.util.stream.Stream

@TestTemplate
@Target(AnnotationTarget.FUNCTION)
@ExtendWith(RetryTestExtension::class)
annotation class Retry(val count: Int = 1)

class RetryTestExtension : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(context: ExtensionContext?): Boolean {
        return context!!.testMethod.map { it.isAnnotationPresent(Retry::class.java) }.orElse(false)
    }

    override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext> {
        val testMethod = context!!.testMethod.orElseThrow { shouldNeverHappen() }
        val retryAnnotation = AnnotationSupport.findAnnotation(testMethod, Retry::class.java).orElseThrow { shouldNeverHappen() }

        val retryCount = retryAnnotation.count
        RetryContext.erect(context, retryCount)

        return IntStream.rangeClosed(1, retryCount + 1).mapToObj { RetryTemplate() }
    }

    private fun shouldNeverHappen() = ExtensionContextException("should never happen because this is check beforehand")
}

class ShouldKeepTrying : ExecutionCondition {
    override fun evaluateExecutionCondition(executionContext: ExtensionContext?): ConditionEvaluationResult {
        val retryContext = RetryContext.from(executionContext)
        return when (retryContext.alreadySuccessful()) {
            true -> ConditionEvaluationResult.disabled("already had one successful run")
            false -> {
                retryContext.nextRun()
                ConditionEvaluationResult.enabled("no success yet")
            }
        }
    }
}

class RetryContext(val retries: Int, var invocationCount: Int = 0, var failedCount: Int = 0) : ExtensionContext.Store.CloseableResource {
    companion object {
        fun erect(context: ExtensionContext, retryCount: Int) {
            storeFor(context).getOrComputeIfAbsent(context.requiredTestMethod.name, { RetryContext(retries = retryCount) }, RetryContext::class.java)
        }

        fun from(context: ExtensionContext?): RetryContext {
            return storeFor(context!!).get(context.requiredTestMethod.name, RetryContext::class.java)
        }

        private fun storeFor(context: ExtensionContext): ExtensionContext.Store {
            val namespace = ExtensionContext.Namespace.create(RetryTestExtension::class.java)
            return context.parent.get().getStore(namespace)
        }
    }

    fun failed() {
        failedCount += 1
    }

    fun alreadySuccessful(): Boolean {
        println("[d] already successful")
        return invocationCount > failedCount
    }

    fun nextRun() {
        println("[d] next run")
        invocationCount += 1
    }

    fun isRetryLeft(): Boolean {
        return failedCount <= retries
    }

    override fun close() {
        println("our store was destroyed")
    }
}

class RetryInformation(val invocation: Int) {

}

class BetterName : TestExecutionExceptionHandler {
    override fun handleTestExecutionException(context: ExtensionContext?, throwable: Throwable?) {
        val retryContext = RetryContext.from(context)
        retryContext.failed()
        if (retryContext.isRetryLeft()) {
            throw TestAbortedException("this is ugly")
        } else {
            throw throwable!!
        }
    }
}

class RetryInformationParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return RetryInformation::class.java == parameterContext!!.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        val context = RetryContext.from(extensionContext)
        return RetryInformation(context.invocationCount)
    }
}

class RetryTemplate : TestTemplateInvocationContext {

    override fun getDisplayName(invocationIndex: Int): String {
        return if (invocationIndex == 1) "initial run" else "retry " + (invocationIndex - 1)
    }

    override fun getAdditionalExtensions(): MutableList<Extension> {
        return mutableListOf(ShouldKeepTrying(), BetterName(), RetryInformationParameterResolver())
    }
}

