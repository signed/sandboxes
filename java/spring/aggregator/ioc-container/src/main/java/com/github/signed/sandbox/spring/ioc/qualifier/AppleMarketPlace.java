package com.github.signed.sandbox.spring.ioc.qualifier;

import org.springframework.stereotype.Component;

@Component
@Platform(Platform.OperatingSystems.IOS)
class AppleMarketPlace implements MarketPlace {

    @Override
    public String toString() {
        return "apple";
    }
}
