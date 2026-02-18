package org.example;

import net.jqwik.api.Example;
import org.assertj.core.api.Assertions;

import static java.util.Arrays.asList;

class MyCalculatorExamples {

	@Example
	void summingUpZeros() {
		int result = new MyCalculator().sum(asList(0, 0, 0));
		Assertions.assertThat(result).isEqualTo(0);
	}

	@Example
	void summingUpTwoNumbers() {
		int result = new MyCalculator().sum(asList(1, 41));
		Assertions.assertThat(result).isEqualTo(42);
	}
}