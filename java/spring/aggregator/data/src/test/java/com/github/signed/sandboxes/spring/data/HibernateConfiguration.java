package com.github.signed.sandboxes.spring.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Map;

@Configuration
public class HibernateConfiguration {

    public static HibernateJpaVendorAdapter hibernateConfiguration() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        Map<String, Object> propertyMap = hibernateJpaVendorAdapter.getJpaPropertyMap();
        propertyMap.put("hibernate.show_sql", "true");
        propertyMap.put("hibernate.format_sql", "true");
        propertyMap.put("hibernate.use_sql_comments", "true");

        return hibernateJpaVendorAdapter;
    }


    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return hibernateConfiguration();
    }

}
