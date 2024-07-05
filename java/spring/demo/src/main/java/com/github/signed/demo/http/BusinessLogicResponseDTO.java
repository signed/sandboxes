package com.github.signed.demo.http;

import java.math.BigDecimal;

public class BusinessLogicResponseDTO {
    public final BigDecimal price;

    public BusinessLogicResponseDTO(BigDecimal price) {
        this.price = price;
    }
}
