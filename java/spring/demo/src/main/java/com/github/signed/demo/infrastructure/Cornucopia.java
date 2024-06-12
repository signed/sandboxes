package com.github.signed.demo.infrastructure;

import com.github.signed.demo.core.Product;
import com.github.signed.demo.core.Quantity;
import com.github.signed.demo.core.Warehouse;

public class Cornucopia implements Warehouse {
    @Override
    public StockResult stockFor(Product product) {
        return StockResult.stock(new Quantity(Integer.MAX_VALUE));
    }
}
