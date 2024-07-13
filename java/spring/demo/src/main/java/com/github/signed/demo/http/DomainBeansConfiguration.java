package com.github.signed.demo.http;

import com.github.signed.demo.core.BusinessLogic;
import com.github.signed.demo.core.ProductionBusinessLogic;
import com.github.signed.demo.core.Warehouse;
import com.github.signed.demo.infrastructure.Cornucopia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class DomainBeansConfiguration {

    @Bean
    public Warehouse warehouse() {
        return new Cornucopia();
    }

    @Bean
    @RequestScope
    public BusinessLogic businessLogic(Warehouse warehouse) {
        return new ProductionBusinessLogic(warehouse);
    }
}
