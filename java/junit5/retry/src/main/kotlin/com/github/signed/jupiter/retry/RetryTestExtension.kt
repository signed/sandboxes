package com.github.signed.jupiter.retry

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.*
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
        return Stream.of(RetryTemplate())
    }
}

class RetryInformation {

}

class RetryInformationParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext!!.parameter.type === RetryInformation::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return RetryInformation()
    }
}

class RetryTemplate : TestTemplateInvocationContext {

    override fun getDisplayName(invocationIndex: Int): String {
        return "super.getDisplayName(invocationIndex)"
    }

    override fun getAdditionalExtensions(): MutableList<Extension> {
        return mutableListOf(RetryInformationParameterResolver())
    }
}

