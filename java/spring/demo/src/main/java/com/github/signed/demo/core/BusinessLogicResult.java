package com.github.signed.demo.core;

import java.util.Optional;

public class BusinessLogicResult {

    public static BusinessLogicResult outOfStock() {
        return new BusinessLogicResult(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static BusinessLogicResult success(BusinessLogicOk ok) {
        return new BusinessLogicResult(Optional.of(ok), Optional.empty(), Optional.empty());
    }

    public static BusinessLogicResult ups() {
        return new BusinessLogicResult(Optional.empty(), Optional.empty(), Optional.of("something went wrong"));
    }

    public record BusinessLogicOk(Price price) {
    }

    public final Optional<BusinessLogicOk> ok;
    public final Optional<String> outOfStock;
    public final Optional<String> ups;

    public BusinessLogicResult(Optional<BusinessLogicOk> ok, final Optional<String> outOfStock, final Optional<String> ups) {
        this.ok = ok;
        this.outOfStock = outOfStock;
        this.ups = ups;
    }

}
