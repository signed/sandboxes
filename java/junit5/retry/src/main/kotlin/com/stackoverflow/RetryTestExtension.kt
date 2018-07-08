package com.stackoverflow

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContextException
import org.junit.jupiter.api.extension.TestTemplateInvocationContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider
import org.junit.platform.commons.support.AnnotationSupport
import java.util.stream.IntStream
import java.util.stream.Stream

class RetryTestExtension : TestTemplateInvocationContextProvider {
  override fun supportsTestTemplate(context: ExtensionContext): Boolean {
    return context.testMethod.map { it.isAnnotationPresent(Retry::class.java) }.orElse(false)
  }

  override fun provideTestTemplateInvocationContexts(context: ExtensionContext): Stream<TestTemplateInvocationContext> {
    val annotation = AnnotationSupport.findAnnotation(
        context.testMethod.orElseThrow { ExtensionContextException("Must be annotated on method") },
        Retry::class.java
    ).orElseThrow { ExtensionContextException("${Retry::class.java} not found on method") }

    checkValidRetry(annotation)

    return IntStream.rangeClosed(1, annotation.invocationCount)
        .mapToObj { RetryTemplateContext(it, annotation.invocationCount, annotation.minSuccess) }
  }

  private fun checkValidRetry(annotation: Retry) {
    if (annotation.invocationCount < 1) {
      throw ExtensionContextException("${annotation.invocationCount} must be greater than or equal to 1")
    }
    if (annotation.minSuccess < 1 || annotation.minSuccess > annotation.invocationCount) {
      throw ExtensionContextException("Invalid ${annotation.minSuccess}")
    }
  }
}