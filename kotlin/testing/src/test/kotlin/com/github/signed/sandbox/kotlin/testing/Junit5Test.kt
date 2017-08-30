package com.github.signed.sandbox.kotlin.testing

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.endsWith
import com.natpryce.hamkrest.startsWith
import org.junit.jupiter.api.Test

class Junit5Test {

    @Test fun testOk() {
        assert.that("actual", startsWith("a") and endsWith("al") and !containsSubstring("flup"))
    }
}