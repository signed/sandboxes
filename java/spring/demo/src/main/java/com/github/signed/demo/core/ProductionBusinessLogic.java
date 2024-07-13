package com.github.signed.demo.core;

import java.math.BigDecimal;

public class ProductionBusinessLogic implements BusinessLogic {

    private final Warehouse warehouse;

    public ProductionBusinessLogic(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
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
