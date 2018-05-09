package com.github.signed.sandbox.spring.ioc.qualifier;

import org.springframework.stereotype.Component;

@Component
@Platform(Platform.OperatingSystems.ANDROID)
class GoogleMarketPlace implements MarketPlace {

    @Override
    public String toString() {
        return "android";
    }
}