package com.github.signed.demo.http;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BusinessLogicRequestDTO {
    @NotNull
    @NotEmpty
    public String product;

    @Positive
    public int quantity;
}
