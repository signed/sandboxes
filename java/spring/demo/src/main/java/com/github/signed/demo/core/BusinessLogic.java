package com.github.signed.demo.core;

public interface BusinessLogic {
    BusinessLogicResult callLogic(Product product, Quantity requestedQuantity);
}
