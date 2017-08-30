package com.github.signed.sandbox.kotlin.testing

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class KotlintestTest : StringSpec() {
    init {
        "String size" {
            forAll { a: String, b: String ->
                (a + b).length == a.length + b.length
            }
        }

        "length should return size of string" {
            "hello".length shouldBe 5
        }
    }
}