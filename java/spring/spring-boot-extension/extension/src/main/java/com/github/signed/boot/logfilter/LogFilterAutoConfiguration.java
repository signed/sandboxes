package com.github.signed.boot.logfilter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class LogFilterAutoConfiguration {

    @Bean
    public FilterRegistrationBean requestLoggingFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setName("RequestLogFilter");
        filterRegistrationBean.setOrder(HIGHEST_PRECEDENCE);
        filterRegistrationBean.setFilter(new RequestLogFilter());
        return filterRegistrationBean;
    }

    private static class RequestLogFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            System.out.println("enter " + getFilterName());
            filterChain.doFilter(request, response);
            System.out.println("exit " + getFilterName());
        }
    }
}
