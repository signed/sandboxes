package com.github.signed.demo.core;

import java.math.BigDecimal;

public class BusinessLogic {

    private final Warehouse warehouse;

    public BusinessLogic(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BusinessLogicResult callLogic(Product product, Quantity requestedQuantity) {
        final Warehouse.StockResult stock = warehouse.stockFor(product);
        if (stock.quantity.isEmpty()) {
            return BusinessLogicResult.ups();
        }
        final Quantity itemsInStock = stock.quantity.get();
        if (itemsInStock.number() < requestedQuantity.number()) {
            return BusinessLogicResult.outOfStock();
        }
        final Price standInItemPrice = new Price(BigDecimal.valueOf(17));
        final BusinessLogicResult.BusinessLogicOk ok = new BusinessLogicResult.BusinessLogicOk(standInItemPrice.times(requestedQuantity));
        return BusinessLogicResult.success(ok);
    }

}
