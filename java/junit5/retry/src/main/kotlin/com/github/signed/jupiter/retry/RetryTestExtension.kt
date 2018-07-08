package com.github.signed.jupiter.retry

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport
import org.opentest4j.TestAbortedException
import java.util.*
import java.util.Spliterators.spliteratorUnknownSize
import java.util.stream.Stream
import java.util.stream.StreamSupport.stream

@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS])
@TestTemplate
@ExtendWith(RetryTestExtension::class)
annotation class Retry(val times: Int = 1)

class RetryTestExtension : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(context: ExtensionContext): Boolean {
        return context.testMethod.map { AnnotationSupport.isAnnotated(it, Retry::class.java) }.orElse(false)
    }

    override fun provideTestTemplateInvocationContexts(extensionContext: ExtensionContext): Stream<TestTemplateInvocationContext> {
        val retryAnnotation = AnnotationSupport.findAnnotation(extensionContext.testMethod, Retry::class.java).orElseThrow { shouldNeverHappen() }
        val retryCount = retryAnnotation.times

        val retryContext = RetryContext.erectIn(extensionContext, retryCount)
        val spliterator = spliteratorUnknownSize<TestTemplateInvocationContext>(ConditionalRetryTemplateIterator(retryContext), Spliterator.NONNULL)
        return stream<TestTemplateInvocationContext>(spliterator, false)
    }

    private fun shouldNeverHappen() = ExtensionContextException("should never happen because this is check beforehand")
}

class ConditionalRetryTemplateIterator(private val retryContext: RetryContext) : Iterator<RetryTemplate> {
    override fun hasNext(): Boolean {
        return retryContext.keepTrying()
    }

    override fun next(): RetryTemplate {
        return RetryTemplate()
    }
}

class ShouldKeepTrying : ExecutionCondition {
    override fun evaluateExecutionCondition(executionContext: ExtensionContext): ConditionEvaluationResult {
        RetryContext.from(executionContext).startNextTry()
        return ConditionEvaluationResult.enabled("no success yet and retries left")
    }
}

class RetryContext(val retries: Int) : ExtensionContext.Store.CloseableResource {
    companion object {
        fun erectIn(context: ExtensionContext, retryCount: Int): RetryContext {
            return storeFor(context).getOrComputeIfAbsent(context.requiredTestMethod.name, { RetryContext(retries = retryCount) }, RetryContext::class.java)
        }

        fun from(context: ExtensionContext): RetryContext {
            return storeFor(context).get(context.requiredTestMethod.name, RetryContext::class.java)
        }

        private fun storeFor(context: ExtensionContext): ExtensionContext.Store {
            val namespace = ExtensionContext.Namespace.create(RetryTestExtension::class.java)
            return context.getStore(namespace)
        }
    }

    private var invocationCount: Int = 0
    private var failedCount: Int = 0

    fun failed() {
        failedCount += 1
    }

    fun invocationCount(): Int {
        return invocationCount
    }

    fun startNextTry() {
        invocationCount += 1
    }

    fun isRetryLeft(): Boolean {
        return failedCount <= retries
    }

    fun keepTrying(): Boolean {
        return !alreadySuccessful() && isRetryLeft()
    }

    private fun alreadySuccessful(): Boolean {
        return invocationCount > failedCount
    }

    override fun close() {
        println("our store was destroyed")
    }
}

class RetryInformation(val invocation: Int)

class BetterName : TestExecutionExceptionHandler {
    override fun handleTestExecutionException(context: ExtensionContext, throwable: Throwable?) {
        val retryContext = RetryContext.from(context)
        retryContext.failed()
        if (retryContext.isRetryLeft()) {
            throw TestAbortedException("retry left")
        } else {
            throw throwable!!
        }
    }
}

class RetryInformationParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext): Boolean {
        return RetryInformation::class.java == parameterContext!!.parameter.type
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext): Any {
        val context = RetryContext.from(extensionContext)
        return RetryInformation(context.invocationCount())
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

