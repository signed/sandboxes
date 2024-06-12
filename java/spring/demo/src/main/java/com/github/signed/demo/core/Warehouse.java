package com.github.signed.demo.core;

import java.util.Optional;

public interface Warehouse {
    class StockResult {
        public  final Optional<Quantity> quantity;

        public static StockResult stock(Quantity quantity){
            return new StockResult(Optional.of(quantity));
        }

        public static StockResult ups(){
            return new StockResult(Optional.empty());
        }

        public StockResult(Optional<Quantity> quantity) {
            this.quantity = quantity;
        }

    }

    StockResult stockFor(Product product);
}
