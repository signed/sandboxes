package com.stackoverflow

import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.TestTemplateInvocationContext

class RetryTemplateContext(
  private val invocation: Int,
  private val maxInvocations: Int,
  private val minSuccess: Int
) : TestTemplateInvocationContext {
  override fun getDisplayName(invocationIndex: Int): String {
    return "Invocation number $invocationIndex (requires $minSuccess success)"
  }

  override fun getAdditionalExtensions(): MutableList<Extension> {
    return mutableListOf(
            RetryingTestExecutionExtension(invocation, maxInvocations, minSuccess)
    )
  }
}