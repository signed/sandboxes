package com.github.signed.demo.core;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price times(Quantity requestedQuantity) {
        return new Price(this.value.multiply(BigDecimal.valueOf(requestedQuantity.number())));
    }
}
