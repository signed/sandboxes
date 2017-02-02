package com.github.signed.sandboxes.spring.advices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.signed.sandboxes.spring.advices.configuration.AdvisedControllerApplicationConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class AdvisedControllerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdvisedControllerApplicationConfiguration.class, args);
    }

    @Bean
    public Reporter reporter() {
        return new LoggingReporter();
    }

    @Bean
    public BusinessLogic boringBusinessLogic(){
        return new BoringBusinessLogic();
    }

    @Bean
    public FilterRegistrationBean aFilter(Reporter reporter) {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setName("beforeAfterForwardLogging");
        filter.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        filter.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                try{
                    reporter.filterEnter();
                    filterChain.doFilter(request, response);
                }finally {
                    reporter.filterExit();
                }
            }
        });
        return filter;
    }

    private static class LoggingReporter implements Reporter {
        private static final Logger logger = LoggerFactory.getLogger(LoggingReporter.class);

        @Override
        public void filterEnter() {
            logger.info("filterEnter");
        }

        @Override
        public void filterExit() {
            logger.info("filterExit");
        }

        @Override
        public void controllerAdvice() {
            logger.info("controllerAdvice");
        }

        @Override
        public void aspectEnter() {
            logger.info("aspectEnter");
        }

        @Override
        public void aspectExit() {
            logger.info("aspectExit");
        }

        @Override
        public void controller() {
            logger.info("controller");
        }
    }

    private static class BoringBusinessLogic implements BusinessLogic {
        @Override
        public void executeLogic() {

        }
    }
}
