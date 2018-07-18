package com.github.signed.jupiter.retry

import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.platform.engine.TestDescriptor
import org.junit.platform.engine.TestExecutionResult
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.engine.support.descriptor.MethodSource
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestIdentifier
import org.junit.platform.launcher.TestPlan
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
import org.junit.platform.launcher.core.LauncherFactory

class RetryTestExtensionTest {

    @Retry(times = 2)
    fun `jump and run`(retryInformation: RetryInformation) {
        println("invocation: " + retryInformation.invocation)
        when (retryInformation.invocation) {
            3 -> println("success")
            else -> {
                println("fail")
                throw RuntimeException("unlucky")
            }
        }
    }

    @Test
    fun `name it later`() {
        val discoveryRequest = LauncherDiscoveryRequestBuilder.request().selectors(selectClass(SuccessfulOnFirstTry::class.java)).build()

        val launcher = LauncherFactory.create()
        val report = CollectingTestExecutionListener()
        launcher.execute(discoveryRequest, report)

        val jupm = report.finishedExecutionForMethodWithName("firstTry")
        jupm.resultStatus `should be` TestExecutionResult.Status.SUCCESSFUL
        report.children(jupm.testIdentifier)

        println(report)
    }
}

class CollectingTestExecutionListener : TestExecutionListener {
    private val mutableListOf = mutableListOf<FinishedExecution>()
    private var testPlan: TestPlan? = null

    override fun testPlanExecutionFinished(testPlan: TestPlan?) {
        this.testPlan = testPlan
    }

    override fun executionFinished(testIdentifier: TestIdentifier, testExecutionResult: TestExecutionResult) {
        mutableListOf.add(FinishedExecution(testIdentifier, testExecutionResult))
    }

    fun finishedExecutionForMethodWithName(methodName: String): FinishedExecution {

        return mutableListOf.first {
            it.testIdentifier.type == TestDescriptor.Type.CONTAINER && it.testIdentifier.source
                    .filter { it is MethodSource }.map { it as MethodSource }
                    .filter { methodName == it.methodName }
                    .map { true }.orElse(false)
        }
    }

    fun children(testIdentifier: TestIdentifier): List<FinishedExecution>{
        var children = testPlan!!.getChildren(testIdentifier)
        return listOf()
    }
}


class FinishedExecution(val testIdentifier: TestIdentifier, val testExecutionResult: TestExecutionResult) {

    val resultStatus : TestExecutionResult.Status
    get() = testExecutionResult.status

}

class SuccessfulOnFirstTry {

    @Retry(times = 2)
    fun firstTry(retryInformation: RetryInformation) {
        when (retryInformation.invocation) {
            2 -> true
            else -> throw RuntimeException()
        }
        assertTrue(true) { "first try success" }
    }
}