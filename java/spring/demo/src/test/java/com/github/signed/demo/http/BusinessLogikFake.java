package com.github.signed.demo.http;

import com.github.signed.demo.core.BusinessLogic;
import com.github.signed.demo.core.BusinessLogicResult;
import com.github.signed.demo.core.Product;
import com.github.signed.demo.core.Quantity;

public class BusinessLogikFake implements BusinessLogic {

    private BusinessLogicResult result = BusinessLogicResult.ups();

    public void respondWith(BusinessLogicResult result) {
        this.result = result;
    }

    @Override
    public BusinessLogicResult callLogic(Product product, Quantity requestedQuantity) {
        return this.result;
    }
}
