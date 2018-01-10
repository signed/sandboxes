package com.github.signed.boot.environment;

import org.apache.log4j.MDC;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class RequestIdentifierAutoConfiguration {
    @Bean(name = "requestIdFilter")
    public FilterRegistrationBean requestLoggingFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("RequestIdFilter");
        filterRegistrationBean.setOrder(HIGHEST_PRECEDENCE);
        filterRegistrationBean.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                System.out.println("enter mdc");
                try {
                    MDC.put("requestId", UUID.randomUUID());
                    filterChain.doFilter(request, response);
                } finally {
                    MDC.clear();
                }
                System.out.println("exit mdc");
            }
        });
        return filterRegistrationBean;
    }

}
