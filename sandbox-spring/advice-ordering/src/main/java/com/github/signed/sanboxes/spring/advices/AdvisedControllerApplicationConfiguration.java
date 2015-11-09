package com.github.signed.sanboxes.spring.advices;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {AdvisedControllerApplication.class})
public class AdvisedControllerApplicationConfiguration {

}
